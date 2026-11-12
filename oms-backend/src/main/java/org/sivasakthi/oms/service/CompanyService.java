package org.sivasakthi.oms.service;

import lombok.RequiredArgsConstructor;
import org.sivasakthi.oms.dto.*;
import org.sivasakthi.oms.entity.*;
import org.sivasakthi.oms.exception.SSProgrammingException;
import org.sivasakthi.oms.exception.SSResourceException;
import org.sivasakthi.oms.mapper.CompanyMapper;
import org.sivasakthi.oms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyAgentRepo companyAgentRepo;
    private final AgentProductService agentProductService;
    private final CompanyPurchaseRepo companyPurchaseRepo;
    private final CompanyCaseRepo caseRepo;
    private final CompanySubCaseRepo subCaseRepo;
    private final CompanySubCaseItemRepo subCaseItemRepo;
    private final CompanyPaymentRepo companyPaymentRepo;

    // Company
    @Transactional
    public CompanyDto createCompany(String name) {
        Company company = new Company();
        company.setName(name);
        Company createdCompany = companyRepo.save(company);

        CompanyAgent companyAgent = new CompanyAgent();
        companyAgent.setCompany(createdCompany);
        companyAgentRepo.save(companyAgent);
        return CompanyMapper.toDto(createdCompany);
    }

    @Transactional
    public CompanyDto updateCompany(long id, String name) {
        Company company = getCompanyById(id);
        company.setName(name);
        return CompanyMapper.toDto(companyRepo.save(company));
    }

    public List<CompanyDto> getCompanies() {
        return companyRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Company::getId).reversed())
                .map(this::addAdditionalInfo)
                .toList();
    }

    @Transactional
    public String deleteCompanyById(long id) {
        Company company = getCompanyById(id);
        List<CompanyAgent> companyAgents = companyAgentRepo.findByCompany(company);

        if(companyAgents.size() > 1) {
            throw new SSProgrammingException("Company is associated with multiple agents");
        }

        companyAgentRepo.delete(companyAgents.getFirst());
        companyRepo.delete(company);
        return "Company with Id - " + id + " has been deleted successfully";
    }

    public Company getCompanyById(long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new SSResourceException("Unknown Company Id : " + id));
    }

    public CompanyDto getCompanyDetails(long id) {
        Company company = getCompanyById(id);
        CompanyAgent agent = companyAgentRepo.findByCompanyAndAgent(company, null)
                .orElseThrow(() -> new SSResourceException("Unable to get agent info"));

        CompanyDto companyDto = CompanyMapper.toDto(company);
        companyDto.setAgentId(agent.getId());
        return companyDto;
    }

    //Company - Purchases
    public PurchasesDto getPurchases(long id) {
        PurchasesDto purchasesDto = new PurchasesDto();

        Company company = getCompanyById(id);
        List<CompanyPurchase> purchases = companyPurchaseRepo.findByCompany(company);

        List<PurchaseRowDto> purchaseRowDtos = new ArrayList<>();
        int totalCases = 0;
        double totalAmount = 0;
        for(CompanyPurchase purchase: purchases) {
            PurchaseRowDto purchaseRowDto = new PurchaseRowDto();

            double purchaseAmount = 0;

            List<CompanyCase> cases = caseRepo.findByPurchase(purchase);
            for(CompanyCase companyCase: cases) {
                purchaseAmount = purchaseAmount + getCaseAmount(companyCase);
            }
            List<CompanySubCase> subCases = subCaseRepo.findByPurchase(purchase);
            for(CompanySubCase subCase: subCases) {
                purchaseAmount = purchaseAmount + getSubCaseAmount(subCase);
            }

            purchaseRowDto.setId(purchase.getId());
            purchaseRowDto.setDate(purchase.getDate());
            purchaseRowDto.setNoOfCases(purchase.getNoOfCases());
            purchaseRowDto.setAmount(purchaseAmount);
            purchaseRowDto.setStatus(purchase.getStatus().label);
            purchaseRowDtos.add(purchaseRowDto);

            totalCases = totalCases + purchase.getNoOfCases();
            totalAmount = totalAmount + purchaseAmount;
        }

        purchasesDto.setTotalCases(totalCases);
        purchasesDto.setTotalAmount(totalAmount);
        purchasesDto.setPurchases(purchaseRowDtos.stream()
                .sorted(Comparator.comparing(PurchaseRowDto::getId).reversed())
                .toList());
        return purchasesDto;
    }

    //Company - Payments
    public PaymentsDto getPayments(long id) {
        PaymentsDto paymentsDto = new PaymentsDto();

        Company company = getCompanyById(id);
        List<CompanyPayment> payments = companyPaymentRepo.findByCompany(company);

        double totalAmount = 0;
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for(CompanyPayment payment: payments) {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(payment.getId());
            paymentDto.setDate(payment.getDate());
            paymentDto.setMode(payment.getMode().label);
            paymentDto.setAmount(payment.getAmount());
            paymentDto.setDescription(payment.getDescription());

            totalAmount = totalAmount + payment.getAmount();
            paymentDtos.add(paymentDto);
        }

        paymentsDto.setTotalAmount(totalAmount);
        paymentsDto.setPayments(paymentDtos.stream()
                .sorted(Comparator.comparing(PaymentDto::getId))
                .toList());
        return paymentsDto;
    }

    //private methods
    private CompanyDto addAdditionalInfo(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());

        PurchasesDto purchases = getPurchases(company.getId());
        companyDto.setTotalCases(purchases.getTotalCases());
        companyDto.setTotalPurchases(purchases.getTotalAmount());

        PaymentsDto payments = getPayments(company.getId());
        double balance = purchases.getTotalAmount() - payments.getTotalAmount();
        companyDto.setBalance(balance);

        CompanyAgent companyAgent = companyAgentRepo.findByCompanyAndAgent(company, null)
                .orElseThrow(() -> new SSResourceException("Unknown agent"));
        List<CompanyProductDto> products = agentProductService.getProducts(companyAgent.getId());
        companyDto.setAgentId(companyAgent.getId());
        companyDto.setTotalProducts(products.size());
        return companyDto;
    }

    private double getCaseAmount(CompanyCase companyCase) {
        int perCase = companyCase.getCostPrice().getCompanyProduct().getPerCase();
        int quantity = companyCase.getQuantity() * perCase;
        return quantity * companyCase.getCostPrice().getPrice();
    }

    private double getSubCaseAmount(CompanySubCase subCase) {
        List<CompanySubCaseItem> items = subCaseItemRepo.findBySubCase(subCase);
        double subCaseAmount = 0;
        for(CompanySubCaseItem item: items) {
            subCaseAmount = subCaseAmount + (item.getQuantity() * item.getCostPrice().getPrice());
        }
        return subCaseAmount;
    }
}
