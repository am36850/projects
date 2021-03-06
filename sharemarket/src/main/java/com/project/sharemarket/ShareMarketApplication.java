package com.project.sharemarket;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class ShareMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareMarketApplication.class, args);
    }
}
