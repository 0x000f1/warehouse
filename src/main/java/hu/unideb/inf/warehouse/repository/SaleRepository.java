package hu.unideb.inf.warehouse.repository;

import hu.unideb.inf.warehouse.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {}

