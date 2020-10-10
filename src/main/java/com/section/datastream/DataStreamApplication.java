package com.section.datastream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DataStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataStreamApplication.class, args);
	}

}
