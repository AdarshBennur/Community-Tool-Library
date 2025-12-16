package com.library.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Tool Service Application
 * 
 * Manages tools available in the community library.
 * Registers with Eureka Server for service discovery.
 * 
 * @EnableDiscoveryClient - Enables registration with Eureka Server
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ToolServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolServiceApplication.class, args);
        System.out.println("✅ Tool Service started successfully on port 8082");
    }
}
