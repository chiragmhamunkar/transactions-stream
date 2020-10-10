package com.section.datastream.util;

import com.section.datastream.model.TransactionDTO;
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
        List<TransactionDTO> transactionDTOS = CSVUtil.read(path, TransactionDTO.class);
        Assertions.assertEquals(2, transactionDTOS.size());
    }
}
