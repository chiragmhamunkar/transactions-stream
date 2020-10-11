package com.section.datastream.exception;

public class NoTransactionFound extends RuntimeException{

    public NoTransactionFound(int transactionId){
        super("No transaction found for id: " + transactionId);
    }
}
