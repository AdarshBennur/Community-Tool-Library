package com.library.borrow.client;

import com.library.borrow.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * UserClient - Feign Client for User Service
 * 
 * This interface uses Spring Cloud OpenFeign to communicate with User Service.
 * The service name "USER-SERVICE" must match the spring.application.name in
 * User Service.
 * 
 * Feign will automatically:
 * 1. Discover User Service using Eureka
 * 2. Make HTTP calls to User Service
 * 3. Handle serialization/deserialization
 */
@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    /**
     * Call User Service to get user by ID
     * Maps to: GET http://USER-SERVICE/users/{id}
     */
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
