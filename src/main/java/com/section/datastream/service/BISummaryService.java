package com.section.datastream.service;

import com.section.datastream.model.Product;
import com.section.datastream.model.Transaction;
import com.section.datastream.model.summary.CitySummary;
import com.section.datastream.model.summary.ProductSummary;
import com.section.datastream.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class will take care of aggregating the result of transactions so that
 * retrieval of aggregated queries is faster
 */
@Service
public class BISummaryService {

    @Autowired
    ProductService productService;

    private ConcurrentHashMap<Integer, ConcurrentHashMap<LocalDate, Double>> daySummaryByProductId = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ConcurrentHashMap<LocalDate, Double>> daySummaryByCityName = new ConcurrentHashMap<>();

    public void addTransaction(Transaction transaction){
        addForProductSummary(transaction);
        addForCitySummary(transaction);
    }

    private void addForCitySummary(Transaction transaction) {
        String city = transaction.getProduct().getProductManufacturingCity();
        LocalDate transactionDay = transaction.getTransactionDatetime().toLocalDate();
        ConcurrentHashMap<LocalDate, Double> daySummary = daySummaryByCityName.computeIfAbsent(city, p -> new ConcurrentHashMap<>());
        daySummary.computeIfAbsent(transactionDay, td -> 0d);
        daySummary.computeIfPresent(transactionDay, (k, v) -> v + transaction.getAmount());

    }

    private void addForProductSummary(Transaction transaction) {
        int productId = transaction.getProductId();
        LocalDate transactionDay = transaction.getTransactionDatetime().toLocalDate();
        ConcurrentHashMap<LocalDate, Double> daySummary = daySummaryByProductId.computeIfAbsent(productId, p -> new ConcurrentHashMap<>());
        daySummary.computeIfAbsent(transactionDay, td -> 0d);
        daySummary.computeIfPresent(transactionDay, (k, v) -> v + transaction.getAmount());
    }

    public List<ProductSummary> summaryByProductsForLastNDays(int nDays){
        List<Product> products = productService.fetchProducts();
        List<LocalDate> days = DateUtil.getLastNDays(nDays);
        return products.stream()
                .map(p -> summaryByProductForDays(p, days))
                .collect(Collectors.toList());
    }

    public ProductSummary summaryByProductForDays(Product product, List<LocalDate> days){
        ConcurrentHashMap<LocalDate, Double> daySummary = daySummaryByProductId.get(product.getProductId());
        double totalAmount = getTotalAmount(days, daySummary);
        return new ProductSummary(product.getProductName(), totalAmount, days.get(0), days.get(days.size()-1));

    }

    public List<CitySummary> summaryByCitiesForLastNDays(int nDays){
        List<String> cities = productService.getCityNames();
        List<LocalDate> days = DateUtil.getLastNDays(nDays);
        return cities.stream()
                .map(city -> summaryByCityForDays(city, days))
                .collect(Collectors.toList());
    }

    public CitySummary summaryByCityForDays(String city, List<LocalDate> days){
        ConcurrentHashMap<LocalDate, Double> daySummary = daySummaryByCityName.get(city);
        double totalAmount = getTotalAmount(days, daySummary);
        return new CitySummary(city, totalAmount, days.get(0), days.get(days.size()-1));

    }

    private double getTotalAmount(List<LocalDate> days, ConcurrentHashMap<LocalDate, Double> daySummary) {
        double totalAmount = 0d;
        if (!Objects.isNull(daySummary)) {
            totalAmount = days.stream()
                    .map(day -> daySummary.getOrDefault(day, 0d))
                    .reduce(Double::sum)
                    .orElse(0d);
        }
        return totalAmount;
    }
}
