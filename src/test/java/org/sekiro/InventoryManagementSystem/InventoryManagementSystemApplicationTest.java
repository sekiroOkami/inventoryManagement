package org.sekiro.InventoryManagementSystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InventoryManagementSystemApplicationTest {
    @Autowired
    private String helloBean;

    @Test
    void testHelloBean() {
        assertEquals("hello", helloBean);
    }
}