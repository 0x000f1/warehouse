package hu.unideb.inf.warehouse.repository;

import hu.unideb.inf.warehouse.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}

