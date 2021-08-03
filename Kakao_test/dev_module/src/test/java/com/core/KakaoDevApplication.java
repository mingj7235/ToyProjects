package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KakaoDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(KakaoDevApplication.class, args);
    }
}