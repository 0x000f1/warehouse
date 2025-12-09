package hu.unideb.inf.warehouse.repository;

import hu.unideb.inf.warehouse.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAndFindAllWorks() {
        Product p = new Product();
        p.setName("Hammer");
        p.setCategory("Tools");
        p.setPrice(9.99);
        p.setStock(10);

        productRepository.save(p);

        List<Product> list = productRepository.findAll();

        assertEquals(1, list.size());
        assertEquals("Hammer", list.get(0).getName());
        assertEquals("Tools", list.get(0).getCategory());
        assertEquals(9.99, list.get(0).getPrice());
        assertEquals(10, list.get(0).getStock());
    }
}
