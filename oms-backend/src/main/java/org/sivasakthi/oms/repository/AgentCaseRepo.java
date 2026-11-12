package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.AgentCase;
import org.sivasakthi.oms.entity.CostPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentCaseRepo extends JpaRepository<AgentCase, Long> {
    List<AgentCase> findByCostPrice(CostPrice costPrice);
}
