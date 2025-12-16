package com.library.borrow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Borrow Entity - Represents a borrowing transaction
 * 
 * Links a user to a tool they are borrowing.
 * Stores the userId and toolId (not the full objects) to maintain database
 * independence.
 */
@Entity
@Table(name = "borrows")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // Reference to User in User Service

    @Column(nullable = false)
    private Long toolId; // Reference to Tool in Tool Service

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = false)
    private String status; // e.g., "ACTIVE", "RETURNED"
}
