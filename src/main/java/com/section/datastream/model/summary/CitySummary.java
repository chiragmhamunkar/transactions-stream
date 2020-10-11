package com.section.datastream.model.summary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CitySummary {

    private String cityName;
    private double totalAmount;
    private LocalDate from;
    private LocalDate to;
}
