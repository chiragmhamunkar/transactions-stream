package com.section.datastream.service;

import com.section.datastream.model.Product;
import com.section.datastream.util.CSVUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class ProductsService {

    @Value("${products.file.path}")
    private String productsFilePath;

    public List<Product> fetchProducts() throws IOException {
        log.info("Loading products from {}", productsFilePath);
        return CSVUtil.read(Paths.get(productsFilePath), Product.class);
    }
}
