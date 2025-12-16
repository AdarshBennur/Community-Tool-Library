package com.library.borrow.controller;

import com.library.borrow.model.Borrow;
import com.library.borrow.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Borrow Controller - REST API endpoints
 * 
 * Handles HTTP requests for borrow operations.
 */
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    /**
     * POST /borrow - Create a new borrow (DEMONSTRATES INTER-SERVICE COMMUNICATION)
     */
    @PostMapping
    public ResponseEntity<Borrow> createBorrow(@RequestBody Borrow borrow) {
        try {
            Borrow createdBorrow = borrowService.createBorrow(borrow);
            return new ResponseEntity<>(createdBorrow, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /borrow/{id} - Get borrow by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Borrow> getBorrowById(@PathVariable Long id) {
        return borrowService.getBorrowById(id)
                .map(borrow -> new ResponseEntity<>(borrow, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET /borrow/user/{userId} - Get all borrows for a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Borrow>> getBorrowsByUserId(@PathVariable Long userId) {
        List<Borrow> borrows = borrowService.getBorrowsByUserId(userId);
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    /**
     * GET /borrow - Get all borrows
     */
    @GetMapping
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        List<Borrow> borrows = borrowService.getAllBorrows();
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }
}
