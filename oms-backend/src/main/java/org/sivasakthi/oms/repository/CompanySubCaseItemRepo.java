package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.CompanySubCase;
import org.sivasakthi.oms.entity.CompanySubCaseItem;
import org.sivasakthi.oms.entity.CostPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanySubCaseItemRepo extends JpaRepository<CompanySubCaseItem, Long> {
    List<CompanySubCaseItem> findByCostPrice(CostPrice costPrice);
    List<CompanySubCaseItem> findBySubCase(CompanySubCase subCase);
}
