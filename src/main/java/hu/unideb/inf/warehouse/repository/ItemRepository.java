package hu.unideb.inf.warehouse.repository;

import hu.unideb.inf.warehouse.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByField1ContainingIgnoreCase(String text);
}
