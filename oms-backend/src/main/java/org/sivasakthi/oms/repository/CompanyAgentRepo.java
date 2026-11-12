package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.Agent;
import org.sivasakthi.oms.entity.Company;
import org.sivasakthi.oms.entity.CompanyAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyAgentRepo extends JpaRepository<CompanyAgent, Long> {

    List<CompanyAgent> findByCompany(Company company);
    Optional<CompanyAgent> findByCompanyAndAgent(Company company, Agent agent);
}
