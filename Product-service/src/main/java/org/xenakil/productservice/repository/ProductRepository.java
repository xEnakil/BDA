package org.xenakil.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xenakil.productservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findById(String id);
}
