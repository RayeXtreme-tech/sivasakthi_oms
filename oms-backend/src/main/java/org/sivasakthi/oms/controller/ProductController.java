package org.sivasakthi.oms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sivasakthi.oms.dto.CategoryDto;
import org.sivasakthi.oms.dto.ProductCreation;
import org.sivasakthi.oms.dto.ProductDto;
import org.sivasakthi.oms.entity.Product;
import org.sivasakthi.oms.mapper.ProductMapper;
import org.sivasakthi.oms.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductCreation productDto) {
        log.info("ProductController:createProduct endpoint started with payload: {}", productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createNewProduct(productDto));
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductCreation productDto) {
        log.info("ProductController:updateProduct endpoint started with payload: {}", productDto);
        return ResponseEntity.ok(productService.updateProduct(productDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
        log.info("ProductController:getProductById endpoint started with id: {}", id);
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        log.info("ProductController:getProducts endpoint started");
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        log.info("ProductController:getCategories endpoint started");
        return ResponseEntity.ok(productService.getCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable long id) {
        log.info("ProductController:deleteProductById endpoint started with id: {}", id);
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}
