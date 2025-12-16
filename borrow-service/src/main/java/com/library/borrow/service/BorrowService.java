package com.library.borrow.service;

import com.library.borrow.client.ToolClient;
import com.library.borrow.client.UserClient;
import com.library.borrow.dto.ToolDTO;
import com.library.borrow.dto.UserDTO;
import com.library.borrow.model.Borrow;
import com.library.borrow.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Borrow Service - Business logic with inter-service communication
 * 
 * This is the CORE of the microservices communication.
 * Before creating a borrow, it verifies:
 * 1. User exists (via UserClient -> User Service)
 * 2. Tool exists (via ToolClient -> Tool Service)
 */
@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserClient userClient; // Feign client for User Service

    @Autowired
    private ToolClient toolClient; // Feign client for Tool Service

    /**
     * Create a new borrow transaction
     * 
     * DEMONSTRATES INTER-SERVICE COMMUNICATION:
     * 1. Calls User Service to verify user exists
     * 2. Calls Tool Service to verify tool exists
     * 3. Decrements tool availability
     * 4. Creates the borrow record
     * 
     * @Transactional ensures atomicity - if anything fails, everything rolls back
     */
    @Transactional // CRITICAL: Ensures data consistency
    public Borrow createBorrow(Borrow borrow) {
        try {
            // Step 1: Verify user exists by calling User Service
            System.out.println("🔄 Calling User Service to verify user ID: " + borrow.getUserId());
            UserDTO user = userClient.getUserById(borrow.getUserId());
            if (user == null) {
                throw new RuntimeException("User not found with id: " + borrow.getUserId());
            }
            System.out.println("✅ User found: " + user.getName());

            // Step 2: Verify tool exists by calling Tool Service
            System.out.println("🔄 Calling Tool Service to verify tool ID: " + borrow.getToolId());
            ToolDTO tool = toolClient.getToolById(borrow.getToolId());
            if (tool == null) {
                throw new RuntimeException("Tool not found with id: " + borrow.getToolId());
            }
            System.out.println("✅ Tool found: " + tool.getName());

            // Step 3: Check if tool is available
            if (tool.getAvailableQuantity() == null || tool.getAvailableQuantity() <= 0) {
                throw new RuntimeException("Tool '" + tool.getName() + "' is not available for borrowing (quantity: "
                        + tool.getAvailableQuantity() + ")");
            }

            // Step 4: Decrement tool availability (CRITICAL FIX)
            System.out.println("🔄 Decrementing tool quantity for tool ID: " + borrow.getToolId());
            toolClient.decrementToolQuantity(borrow.getToolId());
            System.out.println("✅ Tool quantity decremented");

            // Step 5: Create the borrow record
            borrow.setBorrowDate(LocalDate.now());
            borrow.setStatus("ACTIVE");

            Borrow savedBorrow = borrowRepository.save(borrow);
            System.out.println("✅ Borrow created successfully!");

            return savedBorrow;

        } catch (Exception e) {
            System.err.println("❌ Error creating borrow: " + e.getMessage());
            throw new RuntimeException("Failed to create borrow: " + e.getMessage());
        }
    }

    /**
     * Get borrow by ID
     */
    public Optional<Borrow> getBorrowById(Long id) {
        return borrowRepository.findById(id);
    }

    /**
     * Get all borrows for a specific user
     */
    public List<Borrow> getBorrowsByUserId(Long userId) {
        return borrowRepository.findByUserId(userId);
    }

    /**
     * Get all borrows
     */
    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }
}
