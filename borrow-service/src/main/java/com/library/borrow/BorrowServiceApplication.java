package com.library.borrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Borrow Service Application
 * 
 * CORE MICROSERVICE demonstrating inter-service communication.
 * 
 * @EnableDiscoveryClient - Registers with Eureka Server
 * @EnableFeignClients - Enables Feign for calling other microservices
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // CRITICAL: Enables Feign clients
public class BorrowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BorrowServiceApplication.class, args);
        System.out.println("✅ Borrow Service started successfully on port 8083");
        System.out.println("🔗 Feign clients enabled for inter-service communication");
    }
}
