package org.sivasakthi.oms.mapper;

import org.sivasakthi.oms.dto.ProductDto;
import org.sivasakthi.oms.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setCategory(CategoryMapper.toEntity(productDto.getCategory()));
        return product;
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(CategoryMapper.toDto(product.getCategory()));
        return productDto;
    }
}
