package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.Company;
import org.sivasakthi.oms.entity.CompanyPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyPurchaseRepo extends JpaRepository<CompanyPurchase, Long> {
    List<CompanyPurchase> findByCompany(Company company);
}
