package hu.unideb.inf.warehouse.repository;

import hu.unideb.inf.warehouse.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}