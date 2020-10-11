package com.section.datastream.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    public static List<LocalDate> range(LocalDate from, LocalDate to){
        return from.datesUntil(to.plusDays(1))
                .collect(Collectors.toList());
    }

    public static List<LocalDate> getLastNDays(int nDays){
        LocalDate today = LocalDate.now();
        LocalDate lastNthDay = today.minusDays(nDays - 1); //subtracting cause we have to consider current day also
        return range(lastNthDay, today);
    }


}
