package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Long> {
}
