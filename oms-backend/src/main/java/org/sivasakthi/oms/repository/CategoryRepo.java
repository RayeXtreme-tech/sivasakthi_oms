package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
