package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.CompanyAgent;
import org.sivasakthi.oms.entity.CompanyProduct;
import org.sivasakthi.oms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyProductRepo extends JpaRepository<CompanyProduct, Long> {

    List<CompanyProduct> findByProduct(Product product);
    List<CompanyProduct> findByCompanyAgent(CompanyAgent companyAgent);
}
