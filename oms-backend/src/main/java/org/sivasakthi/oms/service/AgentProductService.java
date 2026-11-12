package org.sivasakthi.oms.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sivasakthi.oms.dto.CompanyProductDto;
import org.sivasakthi.oms.entity.*;
import org.sivasakthi.oms.exception.SSProgrammingException;
import org.sivasakthi.oms.exception.SSResourceException;
import org.sivasakthi.oms.mapper.CostPriceMapper;
import org.sivasakthi.oms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentProductService {

    private final CompanyAgentRepo companyAgentRepo;
    private final CompanyProductRepo companyProductRepo;
    private final CostPriceRepo costPriceRepo;
    private final ProductService productService;
    private final CompanyCaseRepo companyCaseRepo;
    private final CompanySubCaseItemRepo companyItemRepo;
    private final AgentCaseRepo agentCaseRepo;
    private final AgentSubCaseItemRepo agentItemRepo;

    @Transactional
    public CompanyProductDto createProduct(long agentId, CompanyProductDto productDto) {

        Product product = productService.getProductById(productDto.getProductId());
        CompanyAgent agent = companyAgentRepo.findById(agentId)
                .orElseThrow(() -> new SSResourceException("Unknown CompanyAgent Id : " + agentId));

        CompanyProduct companyProduct = new CompanyProduct();
        companyProduct.setProduct(product);
        companyProduct.setCompanyAgent(agent);
        companyProduct.setLocalName(productDto.getName());
        companyProduct.setPerCase(productDto.getPerCase());
        CompanyProduct createdProduct = companyProductRepo.save(companyProduct);

        CostPrice costPrice = new CostPrice();
        costPrice.setCompanyProduct(createdProduct);
        costPrice.setPrice(productDto.getPrice());
        costPrice.setActive(true);
        CostPrice createdCP = costPriceRepo.save(costPrice);

        return CostPriceMapper.toDto(createdCP);
    }

    @Transactional
    public CompanyProductDto updateProduct(CompanyProductDto productDto) {

        Product product = productService.getProductById(productDto.getProductId());
        CostPrice costPrice = getCostPriceById(productDto.getId());

        CompanyProduct companyProduct = costPrice.getCompanyProduct();
        companyProduct.setProduct(product);
        companyProduct.setLocalName(productDto.getName());
        companyProduct.setPerCase(productDto.getPerCase());
        CompanyProduct savedProduct = companyProductRepo.save(companyProduct);

        costPrice.setCompanyProduct(savedProduct);
        if(costPrice.getPrice() == productDto.getPrice()) {
            CostPrice savedCP = costPriceRepo.save(costPrice);
            return CostPriceMapper.toDto(savedCP);
        } else {
            costPrice.setActive(false);
            costPriceRepo.save(costPrice);

            CostPrice newCostPrice = new CostPrice();
            newCostPrice.setCompanyProduct(savedProduct);
            newCostPrice.setPrice(productDto.getPrice());
            newCostPrice.setActive(true);
            newCostPrice = costPriceRepo.save(newCostPrice);
            return CostPriceMapper.toDto(newCostPrice);
        }
    }

    public List<CompanyProductDto> getProducts(long agentId) {

        List<CompanyProductDto> productDtos = new ArrayList<>();

        CompanyAgent companyAgent = companyAgentRepo.findById(agentId)
                .orElseThrow(() -> new SSResourceException("Unable to find agent obj for company : " + agentId));
        List<CompanyProduct> products = companyProductRepo.findByCompanyAgent(companyAgent);

        for(CompanyProduct product: products) {
            CostPrice costPrice = costPriceRepo.findByCompanyProductAndActive(product, true)
                    .orElseThrow(() -> new SSResourceException("Unable to find CostPrice for CP - " + product.getId()));
            productDtos.add(CostPriceMapper.toDto(costPrice));
        }

        return productDtos.stream()
                .sorted(Comparator.comparing(CompanyProductDto::getId))
                .toList();
    }

    @Transactional
    public String deleteProduct(long costPriceId) {

        CostPrice costPrice = getCostPriceById(costPriceId);

        List<CompanyCase> companyCases = companyCaseRepo.findByCostPrice(costPrice);
        List<CompanySubCaseItem> companyItems = companyItemRepo.findByCostPrice(costPrice);
        List<AgentCase> agentCases = agentCaseRepo.findByCostPrice(costPrice);
        List<AgentSubCaseItem> agentItems = agentItemRepo.findByCostPrice(costPrice);
        if(!companyCases.isEmpty() || !companyItems.isEmpty() || !agentCases.isEmpty() || !agentItems.isEmpty()) {
            throw new SSProgrammingException("CP is linked with multiple purchases");
        }

        //TODO - add sales logic

        CompanyProduct product = costPrice.getCompanyProduct();
        List<CostPrice> costPriceList = costPriceRepo.findByCompanyProduct(product);
        if(costPriceList.isEmpty()) {
            throw new SSProgrammingException("Invalid Company Product");
        }

        costPriceRepo.deleteAll(costPriceList);
        companyProductRepo.delete(product);
        return "Product with id : " + costPriceId + " has been deleted successfully";
    }

    public CostPrice getCostPriceById(long id) {
        return costPriceRepo.findById(id)
                .orElseThrow(() -> new SSResourceException("Unknown CP id : " + id));
    }

    @Transactional
    public String createOrUpdateProductInBulk(long agentId, MultipartFile file, boolean isCreate) {
        List<CompanyProductDto> products = extractProductInfo(file, isCreate);
        if(isCreate) {
            for(CompanyProductDto companyProductDto: products) {
                createProduct(agentId, companyProductDto);
            }
        } else {
            for(CompanyProductDto companyProductDto: products) {
                CostPrice oldCostPrice = getCostPriceById(companyProductDto.getId());
                oldCostPrice.setActive(false);
                costPriceRepo.save(oldCostPrice);

                //create new CPP
                CostPrice newCostPrice = new CostPrice();
                newCostPrice.setCompanyProduct(oldCostPrice.getCompanyProduct());
                newCostPrice.setActive(true);
                newCostPrice.setPrice(companyProductDto.getPrice());
                costPriceRepo.save(newCostPrice);
            }
        }

        return products.size() + " products has been added/updated successfully.";
    }

    private List<CompanyProductDto> extractProductInfo(MultipartFile file, boolean isCreate) {
        List<CompanyProductDto> products = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            //skipping header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if(isCreate) {
                    String localName = row.getCell(0).getStringCellValue();
                    long productId = (long) row.getCell(1).getNumericCellValue();
                    double price = row.getCell(2).getNumericCellValue();
                    int perCase = (int) row.getCell(3).getNumericCellValue();

                    CompanyProductDto product = new CompanyProductDto();
                    product.setProductId(productId);
                    product.setName(localName);
                    product.setPrice(price);
                    product.setPerCase(perCase);
                    products.add(product);
                } else {
                    long id = (long) row.getCell(0).getNumericCellValue();
                    double price = row.getCell(1).getNumericCellValue();

                    CompanyProductDto product = new CompanyProductDto();
                    product.setId(id);
                    product.setPrice(price);
                    products.add(product);
                }
            }
        } catch (IOException e) {
            throw new SSProgrammingException("Error occurred while processing file: " + e.getLocalizedMessage());
        }

        return products;
    }
}
