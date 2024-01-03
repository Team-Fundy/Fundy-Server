package com.fundy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(scanBasePackages = {"com.fundy"})
@EntityScan(basePackages = {"com.fundy.jpa"})
@EnableJpaRepositories(basePackages = {"com.fundy.jpa"})
@EnableRedisRepositories(basePackages = {"com.fundy.redis"})
@EnableJpaAuditing
public class FundyApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FundyApiApplication.class, args);
    }
}
