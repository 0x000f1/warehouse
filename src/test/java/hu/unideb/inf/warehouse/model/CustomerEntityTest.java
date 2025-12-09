package hu.unideb.inf.warehouse.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    void customerStoresValuesCorrectly() {
        Customer c = new Customer();
        c.setName("John");
        c.setEmail("john@example.com");
        c.setPhone("123456");

        assertEquals("John", c.getName());
        assertEquals("john@example.com", c.getEmail());
        assertEquals("123456", c.getPhone());
    }
}
