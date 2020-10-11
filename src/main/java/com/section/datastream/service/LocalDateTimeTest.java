package com.section.datastream.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeTest {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDate localDate = localDateTime.toLocalDate();
        System.out.println(localDate);

        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(10);

        System.out.println(from + " " + to);

        from.datesUntil(to)
                .forEach(d -> System.out.println(d));

    }
}
