package org.sivasakthi.oms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sivasakthi.oms.dto.CompanyProductDto;
import org.sivasakthi.oms.service.AgentProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/agent-products")
@Slf4j
@RequiredArgsConstructor
public class AgentProductController {

    private final AgentProductService agentProductService;

    @GetMapping
    public ResponseEntity<List<CompanyProductDto>> getProducts(@RequestParam long agentId) {
        log.info("CompanyController:getProducts endpoint started with id: {}", agentId);
        return ResponseEntity.ok(agentProductService.getProducts(agentId));
    }

    @PostMapping
    public ResponseEntity<CompanyProductDto> createProduct(@RequestParam long agentId,
                                                           @RequestBody CompanyProductDto productDto) {
        log.info("CompanyController:createProduct endpoint started with payload: {}", productDto);
        return ResponseEntity.ok(agentProductService.createProduct(agentId, productDto));
    }

    @PutMapping
    public ResponseEntity<CompanyProductDto> updateProduct(@RequestBody CompanyProductDto productDto) {
        log.info("CompanyController:updateProduct endpoint started with payload: {}", productDto);
        return ResponseEntity.ok(agentProductService.updateProduct(productDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestParam long cppId) {
        log.info("CompanyController:deleteProduct endpoint started with id: {}", cppId);
        return ResponseEntity.ok(agentProductService.deleteProduct(cppId));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createOrUpdateProductInBulk(
            @RequestBody MultipartFile file,
            @RequestParam boolean isCreate,
            @RequestParam long agentId) {
        log.info("CompanyController:createOrUpdateProductsInBulk endpoint started with payload: {}", file.getName());
        return ResponseEntity.ok(agentProductService.createOrUpdateProductInBulk(agentId, file, isCreate));
    }
}
