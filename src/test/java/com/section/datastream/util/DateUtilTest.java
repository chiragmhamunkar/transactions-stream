package com.section.datastream.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateUtilTest {

    @Test
    public void rangeTest(){
        LocalDate from = LocalDate.of(2020, 10, 11);
        LocalDate to = LocalDate.of(2020, 10, 20);

        Assertions.assertEquals(10, DateUtil.range(from, to).size());
    }

    @Test
    public void getLastNDaysTest(){
        Assertions.assertEquals(5, DateUtil.getLastNDays(5).size());
    }
}
