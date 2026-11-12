package org.sivasakthi.oms.repository;

import org.sivasakthi.oms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
