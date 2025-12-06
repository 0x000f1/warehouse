package hu.unideb.inf.warehouse.repository;

import hu.unideb.inf.warehouse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
