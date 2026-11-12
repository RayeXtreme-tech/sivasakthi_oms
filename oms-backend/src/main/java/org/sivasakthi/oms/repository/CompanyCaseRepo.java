package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.CompanyCase;
import org.sivasakthi.oms.entity.CompanyPurchase;
import org.sivasakthi.oms.entity.CostPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyCaseRepo extends JpaRepository<CompanyCase, Long> {
    List<CompanyCase> findByCostPrice(CostPrice costPrice);
    List<CompanyCase> findByPurchase(CompanyPurchase purchase);
}
