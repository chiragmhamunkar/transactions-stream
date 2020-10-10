package com.section.datastream.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    public static final String TRANSACTION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @JsonProperty("transactionId")
    private int transactionId;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("transactionAmount")
    private double amount;

    @JsonProperty("transactionDatetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = TRANSACTION_DATE_FORMAT)
    private LocalDateTime transactionDatetime;

    public Transaction toModel(){
        return Transaction.builder()
                .transactionId(transactionId)
                .productId(productId)
                .amount(amount)
                .transactionDatetime(transactionDatetime)
                .build();
    }
}
