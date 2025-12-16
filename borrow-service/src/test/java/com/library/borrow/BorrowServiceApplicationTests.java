package com.library.borrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

/**
 * Basic test class for Borrow Service
 * 
 * This test excludes Feign client auto-configuration to prevent
 * connection errors when User and Tool services are not available.
 * 
 * For integration tests with Feign, create separate test classes
 * with @MockBean for UserClient and ToolClient.
 */
@SpringBootTest
@EnableAutoConfiguration(exclude = { FeignAutoConfiguration.class })
class BorrowServiceApplicationTests {

    @Test
    void contextLoads() {
        // This test will pass if the Spring application context loads successfully
        // Feign clients are excluded, so no external service dependencies
    }
}
