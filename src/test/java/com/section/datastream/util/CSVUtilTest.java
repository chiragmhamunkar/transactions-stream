package com.section.datastream.util;

import com.section.datastream.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVUtilTest {

    @Test
    public void readTransactionsTest() throws IOException {
        Path path = Paths.get("src/test/resources/dummy-transactions.csv");
        List<Transaction> transactions = CSVUtil.read(path, Transaction.class);
        Assertions.assertEquals(2, transactions.size());
    }
}
