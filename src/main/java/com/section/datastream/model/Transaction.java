package com.section.datastream.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {

    private int transactionId;
    private int productId;
    private double amount;
    private LocalDateTime transactionDatetime;

    private Product product;

}
