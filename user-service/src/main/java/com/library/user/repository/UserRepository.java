package com.library.user.repository;

import com.library.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository - Database access layer
 * 
 * JpaRepository provides built-in CRUD methods:
 * - save() - create/update
 * - findById() - read by ID
 * - findAll() - read all
 * - deleteById() - delete
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method to find user by email
    Optional<User> findByEmail(String email);
}
