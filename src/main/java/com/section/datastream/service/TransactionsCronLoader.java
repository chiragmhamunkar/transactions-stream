package com.section.datastream.service;

import com.section.datastream.model.TransactionDTO;
import com.section.datastream.util.CSVUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TransactionsCronLoader {

    private String transactionDir;

    private WatchService watchService;

    private TransactionService transactionService;

    public TransactionsCronLoader(@Value("${cron.transactions.dir}") String transactionDir,
                                  @Autowired TransactionService transactionService) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(transactionDir);
        path.register(watchService,  StandardWatchEventKinds.ENTRY_CREATE);
        this.transactionDir = transactionDir;
        this.transactionService = transactionService;
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
                List<TransactionDTO> transactionDTOS = loadTransactions(filePath);
                log.info("Found {}", transactionDTOS.size());
                transactionService.addTransactions(transactionDTOS);
            }
            key.reset();
        }

    }

    private List<TransactionDTO> loadTransactions(String filePath) throws IOException {
        return CSVUtil.read(Paths.get(filePath), TransactionDTO.class);
    }
}
