package org.sivasakthi.oms.service;

import lombok.RequiredArgsConstructor;
import org.sivasakthi.oms.dto.CategoryDto;
import org.sivasakthi.oms.dto.ProductCreation;
import org.sivasakthi.oms.dto.ProductDto;
import org.sivasakthi.oms.entity.Category;
import org.sivasakthi.oms.entity.Product;
import org.sivasakthi.oms.exception.SSProgrammingException;
import org.sivasakthi.oms.exception.SSResourceException;
import org.sivasakthi.oms.mapper.CategoryMapper;
import org.sivasakthi.oms.mapper.ProductMapper;
import org.sivasakthi.oms.repository.CategoryRepo;
import org.sivasakthi.oms.repository.CompanyProductRepo;
import org.sivasakthi.oms.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepo categoryRepository;
    private final ProductRepo productRepository;
    private final CompanyProductRepo companyProductRepository;

    @Transactional
    public ProductDto createNewProduct(ProductCreation productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new SSResourceException("Unknown Category Id : " + productDto.getCategoryId()));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(category);
        return ProductMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto updateProduct(ProductCreation productDto) {
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new SSResourceException("Unknown product id : " + productDto.getId()));

        if(isProductValuesChanged(product, productDto)) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new SSResourceException("Unknown category id : " + productDto.getCategoryId()));

            product.setName(productDto.getName());
            product.setCategory(category);
            product = productRepository.save(product);
        }
        return ProductMapper.toDto(product);
    }

    private boolean isProductValuesChanged(Product product, ProductCreation productDto) {
        return product.getCategory().getId() != productDto.getCategoryId() ||
                !product.getName().equals(productDto.getName());
    }

    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new SSResourceException("Unknown Product Id : " + id));
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .map(ProductMapper::toDto)
                .toList();
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll()
                .stream().map(CategoryMapper::toDto)
                .toList();
    }

    public String deleteProductById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new SSResourceException("Unknown product id : " + id));

        if(!companyProductRepository.findByProduct(product).isEmpty()) {
            throw new SSProgrammingException("Product with id: " + id + " linked with some companies");
        }
        productRepository.delete(product);
        return "Product with id : " + id + " has been deleted successfully";
    }
}
