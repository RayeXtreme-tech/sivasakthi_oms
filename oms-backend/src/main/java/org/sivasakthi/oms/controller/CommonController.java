package org.sivasakthi.oms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sivasakthi.oms.entity.Category;
import org.sivasakthi.oms.enums.PurchaseStatus;
import org.sivasakthi.oms.enums.TransactionMode;
import org.sivasakthi.oms.repository.CategoryRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/common")
@Slf4j
@RequiredArgsConstructor
public class CommonController {

    private final CategoryRepo categoryRepo;

    @GetMapping("/transaction-modes")
    public ResponseEntity<List<String>> getTransactionModes() {
        List<String> transactionModes = Arrays.stream(TransactionMode.values())
                .map(transactionMode -> transactionMode.label)
                .toList();
        return ResponseEntity.ok(transactionModes);
    }

    @GetMapping("/purchase-status")
    public ResponseEntity<List<String>> getPurchaseStatus() {
        List<String> purchaseStatusList = Arrays.stream(PurchaseStatus.values())
                .map(purchaseStatus -> purchaseStatus.label)
                .toList();
        return ResponseEntity.ok(purchaseStatusList);
    }

    @GetMapping("/product-categories")
    public ResponseEntity<List<String>> getProductCategories() {
        List<String> categories = categoryRepo.findAll().stream()
                .map(Category::getName)
                .toList();
        return ResponseEntity.ok(categories);
    }
}
