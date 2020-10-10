package com.section.datastream.controller;

import com.section.datastream.model.Transaction;
import com.section.datastream.service.TransactionsComputer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

    @Autowired
    private TransactionsComputer transactionsComputer;

    @GetMapping
    public List<Transaction> fetchTransactions(){
        return transactionsComputer.fetchAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction fetchById(@PathVariable("id")int id){
        return transactionsComputer.fetchTransactionById(id);
    }
}
