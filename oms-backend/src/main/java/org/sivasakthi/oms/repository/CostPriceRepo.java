package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.CompanyProduct;
import org.sivasakthi.oms.entity.CostPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CostPriceRepo extends JpaRepository<CostPrice, Long> {

    Optional<CostPrice> findByCompanyProductAndActive(CompanyProduct product, boolean active);
    List<CostPrice> findByCompanyProduct(CompanyProduct product);
}
