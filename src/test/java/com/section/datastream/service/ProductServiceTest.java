package com.section.datastream.service;

import com.section.datastream.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void notNull(){
        Assertions.assertNotNull(productService);
    }

    @Test
    public void findByIdSuccessTest(){
        int id = 10;
        Assertions.assertEquals(true, productService.findById(id).isPresent());
    }

    @Test
    public void findByIdNotFailureTest(){
        int id = 100;
        Assertions.assertEquals(false, productService.findById(id).isPresent());
    }

    @Test
    public void loadProductsTest() throws IOException {
        List<Product> products = productService.fetchProducts();
        Assertions.assertEquals(4, products.size());
    }

    @Test
    public void loadCities(){
        Assertions.assertEquals(3, productService.getCityNames().size());
    }
}
