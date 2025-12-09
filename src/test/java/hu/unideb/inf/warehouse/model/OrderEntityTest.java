package hu.unideb.inf.warehouse.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @Test
    void orderStoresValuesCorrectly() {
        Order o = new Order();

        LocalDateTime now = LocalDateTime.now();
        o.setOrderDate(now);
        o.setStatus("PENDING");

        assertEquals(now, o.getOrderDate());
        assertEquals("PENDING", o.getStatus());
    }
}
