package com.section.datastream.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/hello")
    public Map<String, String> hello(){
        log.info("Hello controller.");
        return Map.of("key", "Hello, World!");
    }
}
