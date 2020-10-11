package com.section.datastream.controller;

import com.section.datastream.exception.NoTransactionFound;
import com.section.datastream.model.Transaction;
import com.section.datastream.model.summary.CitySummary;
import com.section.datastream.model.summary.ProductSummary;
import com.section.datastream.service.ProductTransactionsBI;
import com.section.datastream.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductTransactionsBI productTransactionsBI;

    @GetMapping
    public List<Transaction> fetchTransactions(){
        return transactionService.fetchAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction fetchById(@PathVariable("id")int id){
        return transactionService.fetchTransactionById(id)
                .orElseThrow(() -> new NoTransactionFound(id));
    }

    @GetMapping("/summaryByProducts/{lastNDays}")
    public List<ProductSummary> summaryByProducts(@PathVariable("lastNDays") int lastNDays){
        return productTransactionsBI.summaryByProductsForLastNDays(lastNDays);
    }

    @GetMapping("/summaryByCities/{lastNDays}")
    public List<CitySummary> summaryByCities(@PathVariable("lastNDays") int lastNDays){
        return productTransactionsBI.summaryByCitiesForLastNDays(lastNDays);
    }

    @ExceptionHandler(value = NoTransactionFound.class)
    public ResponseEntity<String> transactionNotFoundHandler(NoTransactionFound ex){
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception ex){
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
