package hu.unideb.inf.warehouse.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductEntityTest {

    @Test
    void productStoresValuesCorrectly() {
        Product p = new Product();

        p.setName("Item");
        p.setCategory("Tools");
        p.setPrice(39.99);
        p.setStock(50);

        assertEquals("Item", p.getName());
        assertEquals("Tools", p.getCategory());
        assertEquals(39.99, p.getPrice());
        assertEquals(50, p.getStock());
    }
}
