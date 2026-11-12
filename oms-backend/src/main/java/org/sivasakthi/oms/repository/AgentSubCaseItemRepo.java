package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.AgentSubCaseItem;
import org.sivasakthi.oms.entity.CostPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentSubCaseItemRepo extends JpaRepository<AgentSubCaseItem, Long> {
    List<AgentSubCaseItem> findByCostPrice(CostPrice costPrice);
}
