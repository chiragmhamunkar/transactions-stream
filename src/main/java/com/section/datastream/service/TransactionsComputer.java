package com.section.datastream.service;

import com.section.datastream.model.Product;
import com.section.datastream.model.Transaction;
import com.section.datastream.model.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionsComputer {

    private final Map<Integer, Product> productsById;

    private final ConcurrentHashMap<Integer, Transaction> transactionById
            = new ConcurrentHashMap<>();

    public TransactionsComputer(@Autowired ProductsService productsService) throws IOException {
        List<Product> products = productsService.fetchProducts();
        log.info("Found {} products", products.size());
        productsById = products.stream().collect(Collectors.toMap(p -> p.getProductId(),p -> p));
    }

    public void addTransactions(List<TransactionDTO> transactionDTOS){
        List<Transaction> transactions = transactionDTOS
                .stream()
                .map(this::transFormAnnEnrich)
                .collect(Collectors.toList());
        add(transactions);
    }

    private void add(List<Transaction> transactions){
        log.info("Adding {} transactions", transactions.size());
        transactions.forEach(t -> transactionById.put(t.getTransactionId(), t));
    }

    private Transaction transFormAnnEnrich(TransactionDTO transactionDTO){
        Transaction transaction = transactionDTO.toModel();
        transaction.setProduct(productsById.get(transaction.getProductId()));
        return transaction;
    }

    public Transaction fetchTransactionById(int id){
        return transactionById.get(id);
    }

    public List<Transaction> fetchAllTransactions(){
        return transactionById.values().stream().collect(Collectors.toList());
    }


}
