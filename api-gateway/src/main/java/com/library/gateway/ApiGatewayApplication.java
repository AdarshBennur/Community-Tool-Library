package com.library.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway Application
 * 
 * This service acts as a single entry point for all client requests.
 * All requests to backend services go through this gateway.
 * 
 * Routes:
 * - /api/users/** -> USER-SERVICE
 * - /api/tools/** -> TOOL-SERVICE
 * - /api/borrow/** -> BORROW-SERVICE
 * 
 * Port: 8080
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("✅ API Gateway started on port 8080");
        System.out.println("📡 All requests should go through: http://localhost:8080/api/...");
    }
}
