package com.section.datastream.service;

import com.section.datastream.model.Transaction;
import com.section.datastream.util.CSVUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TransactionsLoader {

    private String transactionDir;

    private WatchService watchService;

    public TransactionsLoader(@Value("${cron.transactions.dir}") String transactionDir) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(transactionDir);
        path.register(watchService,  StandardWatchEventKinds.ENTRY_CREATE);
        this.transactionDir = transactionDir;
    }

    @Scheduled(fixedDelayString = "${cron.transactions.fixed.delay}")
    public void cron() throws InterruptedException, IOException {
        log.info("Loading transactions, {}", Instant.now());
        WatchKey key = watchService.poll();
        if(Objects.nonNull(key)) {
            for (WatchEvent<?> event : key.pollEvents()) {
                log.info(
                        "Event kind:" + event.kind()
                                + ". File affected: " + event.context() + ".");
                String filePath = transactionDir + "/" + event.context().toString();
                log.info("Found {}", loadTransactions(filePath));
            }
            key.reset();
        }

    }

    private List<Transaction> loadTransactions(String filePath) throws IOException {
        return CSVUtil.read(Paths.get(filePath), Transaction.class);
    }
}
