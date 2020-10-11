package com.section.datastream.service;

import com.section.datastream.model.Product;
import com.section.datastream.model.Transaction;
import com.section.datastream.model.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {
    private final ConcurrentHashMap<Integer, Transaction> transactionById
            = new ConcurrentHashMap<>();

    @Autowired
    private ProductTransactionsBI productTransactionsBI;
    @Autowired
    private ProductService productService;

    public void addTransactions(List<TransactionDTO> transactionDTOS){
        List<Transaction> transactions = transactionDTOS
                .stream()
                .map(this::transFormAndEnrich)
                .collect(Collectors.toList());
        add(transactions);
    }

    private void add(List<Transaction> transactions){
        log.info("Adding {} transactions", transactions.size());
        transactions.forEach(t -> transactionById.put(t.getTransactionId(), t));
        transactions.forEach(productTransactionsBI:: addTransaction);
    }

    private Transaction transFormAndEnrich(TransactionDTO transactionDTO){
        Transaction transaction = transactionDTO.toModel();
        Product product = productService.findById(transaction.getProductId()).orElseThrow(() -> new RuntimeException("No product fond for product id in transaction"));
        transaction.setProduct(product);
        return transaction;
    }

    public Optional<Transaction> fetchTransactionById(int id){
        return Optional.ofNullable(transactionById.get(id));
    }

    public List<Transaction> fetchAllTransactions(){
        return transactionById.values().stream().collect(Collectors.toList());
    }


}
