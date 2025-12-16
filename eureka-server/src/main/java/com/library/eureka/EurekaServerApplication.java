package com.library.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server Application - Service Discovery
 * 
 * This is the main entry point for the Eureka Server.
 * It acts as a service registry where all microservices register themselves.
 * 
 * @EnableEurekaServer - Enables this application as a Eureka Server
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("✅ Eureka Server started successfully on port 8761");
        System.out.println("🌐 Dashboard: http://localhost:8761");
    }
}
