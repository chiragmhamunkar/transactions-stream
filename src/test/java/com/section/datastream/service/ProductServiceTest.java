package com.section.datastream.service;

import com.section.datastream.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.security.RunAs;
import java.io.IOException;
import java.util.List;

//@ContextConfiguration(classes = ProductsService.class)
@SpringBootTest(classes = ProductsService.class)
public class ProductServiceTest {

    @Autowired
    private ProductsService productsService;

    @Test
    public void notNull(){
        Assertions.assertNotNull(productsService);
    }

    @Test
    public void loadProductsTest() throws IOException {
        List<Product> products = productsService.fetchProducts();
        Assertions.assertEquals(2, products.size());
    }
}
