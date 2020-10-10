package com.section.datastream.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class TransactionsLoader {

    @Scheduled(fixedDelayString = "${cron.transactions.fixed.delay}")
    public void cron(){
        log.info("Hello world, {}", Instant.now());
    }
}
