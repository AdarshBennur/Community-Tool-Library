package com.library.tool.repository;

import com.library.tool.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Tool Repository - Database access layer
 * 
 * JpaRepository provides built-in CRUD methods.
 */
@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

    // Custom query method to find tools by category
    List<Tool> findByCategory(String category);
}
