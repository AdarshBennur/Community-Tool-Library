package com.library.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User Service Application
 * 
 * Manages community members who can borrow tools.
 * Registers with Eureka Server for service discovery.
 * 
 * @EnableDiscoveryClient - Enables registration with Eureka Server
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        System.out.println("✅ User Service started successfully on port 8081");
    }
}
