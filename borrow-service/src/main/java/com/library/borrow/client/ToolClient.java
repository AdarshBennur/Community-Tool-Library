package com.library.borrow.client;

import com.library.borrow.dto.ToolDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ToolClient - Feign Client for Tool Service
 * 
 * This interface uses Spring Cloud OpenFeign to communicate with Tool Service.
 * The service name "TOOL-SERVICE" must match the spring.application.name in
 * Tool Service.
 * 
 * Feign will automatically discover Tool Service via Eureka and make HTTP
 * calls.
 */
@FeignClient(name = "TOOL-SERVICE")
public interface ToolClient {

    /**
     * Call Tool Service to get tool by ID
     * Maps to: GET http://TOOL-SERVICE/tools/{id}
     */
    @GetMapping("/tools/{id}")
    ToolDTO getToolById(@PathVariable("id") Long id);

    /**
     * Call Tool Service to decrement tool quantity
     * Maps to: PUT http://TOOL-SERVICE/tools/{id}/decrement
     */
    @org.springframework.web.bind.annotation.PutMapping("/tools/{id}/decrement")
    ToolDTO decrementToolQuantity(@PathVariable("id") Long id);
}
