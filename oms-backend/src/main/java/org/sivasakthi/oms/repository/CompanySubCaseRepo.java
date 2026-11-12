package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.CompanyPurchase;
import org.sivasakthi.oms.entity.CompanySubCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanySubCaseRepo extends JpaRepository<CompanySubCase, Long> {
    List<CompanySubCase> findByPurchase(CompanyPurchase purchase);
}
