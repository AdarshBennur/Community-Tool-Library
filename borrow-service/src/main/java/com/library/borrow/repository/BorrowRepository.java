package com.library.borrow.repository;

import com.library.borrow.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Borrow Repository - Database access layer
 */
@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    // Custom query to find all borrows by a specific user
    List<Borrow> findByUserId(Long userId);
}
