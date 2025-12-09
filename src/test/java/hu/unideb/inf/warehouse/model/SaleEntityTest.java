package hu.unideb.inf.warehouse.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SaleTest {

    @Test
    void saleStoresValuesCorrectly() {
        Sale s = new Sale();

        s.setQuantity(5);
        s.setTotal(49.99);

        assertEquals(5, s.getQuantity());
        assertEquals(49.99, s.getTotal());
    }
}
