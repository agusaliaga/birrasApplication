package com.santander.birras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableHystrix
public class BirrasApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirrasApplication.class, args);
    }
}
