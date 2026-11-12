package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.Company;
import org.sivasakthi.oms.entity.CompanyPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyPaymentRepo extends JpaRepository<CompanyPayment, Long> {
    List<CompanyPayment> findByCompany(Company company);
}
