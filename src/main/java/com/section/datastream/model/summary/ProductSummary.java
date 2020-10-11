package com.section.datastream.model.summary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProductSummary {
    private String productName;
    private double totalAmount;
    private LocalDate from;
    private LocalDate to;
}
