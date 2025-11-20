package hu.unideb.inf.warehouse;

import hu.unideb.inf.warehouse.model.Person;
import hu.unideb.inf.warehouse.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class WarehouseApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Person p = Person.builder()
                .name("Teszt Elek")
                .dateOfBirth(LocalDate.of(1995, 5, 12))
                .build();

        personRepository.save(p);

        System.out.println("Mentve: " + p);
    }
}
