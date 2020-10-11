package com.section.datastream.service;

import com.section.datastream.model.Product;
import com.section.datastream.util.CSVUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    @Value("${products.file.path}")
    private String productsFilePath;

    //private final List<Product> products;
    private final Map<Integer, Product> productMap;

    public ProductService(@Value("${products.file.path}") String productsFilePath) throws IOException {
        log.info("Loading products from {}", productsFilePath);
        List<Product> products = CSVUtil.read(Paths.get(productsFilePath), Product.class);
        productMap = products.stream().collect(Collectors.toMap(Product::getProductId, p -> p));
        log.info("Loaded {} products in memory", products.size());
    }

    public List<Product> fetchProducts(){
        return productMap.values().stream().collect(Collectors.toUnmodifiableList());
    }

    public Optional<Product> findById(int id){
        return Optional.ofNullable(productMap.get(id));
    }

    //NOTE: Ideally this method should be in City Service but since we don't have any City Master, keeping it here
    //Cities can also be cached as Products are not going to change
    public List<String> getCityNames(){
        return productMap.values().stream().map(Product::getProductManufacturingCity).distinct().collect(Collectors.toList());
    }
}
