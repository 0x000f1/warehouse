package hu.unideb.inf.warehouse.repository;

import hu.unideb.inf.warehouse.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}

