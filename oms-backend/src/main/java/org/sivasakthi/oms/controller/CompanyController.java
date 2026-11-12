package org.sivasakthi.oms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sivasakthi.oms.dto.CompanyDto;
import org.sivasakthi.oms.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestParam String name) {
        log.info("CompanyController:createCompany endpoint started with payload: {}", name);
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestParam String name) {
        log.info("CompanyController:updateCompany endpoint started with payload: {}", name);
        return ResponseEntity.ok(companyService.updateCompany(id, name));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getCompanies() {
        log.info("CompanyController:getCompanies endpoint started");
        return ResponseEntity.ok(companyService.getCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable long id) {
        log.info("CompanyController:getCompanyById endpoint started");
        return ResponseEntity.ok(companyService.getCompanyDetails(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable long id) {
        log.info("CompanyController:deleteCompanyById endpoint started with id: {}", id);
        return ResponseEntity.ok(companyService.deleteCompanyById(id));
    }
}
