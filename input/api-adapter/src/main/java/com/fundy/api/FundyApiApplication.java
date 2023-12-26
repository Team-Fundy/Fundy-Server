package com.fundy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.fundy"})
@EntityScan(basePackages = "com.fundy.jpa")
@EnableJpaRepositories(basePackages = "com.fundy.jpa")
@EnableJpaAuditing
public class FundyApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FundyApiApplication.class, args);
    }
}
