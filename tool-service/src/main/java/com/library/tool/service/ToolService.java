package com.library.tool.service;

import com.library.tool.model.Tool;
import com.library.tool.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Tool Service - Business logic layer
 * 
 * Contains the business logic for tool operations.
 */
@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    /**
     * Create a new tool
     */
    public Tool createTool(Tool tool) {
        return toolRepository.save(tool);
    }

    /**
     * Get tool by ID
     */
    public Optional<Tool> getToolById(Long id) {
        return toolRepository.findById(id);
    }

    /**
     * Get all tools
     */
    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    /**
     * Update an existing tool
     */
    public Tool updateTool(Long id, Tool toolDetails) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + id));

        tool.setName(toolDetails.getName());
        tool.setCategory(toolDetails.getCategory());
        tool.setAvailableQuantity(toolDetails.getAvailableQuantity());

        return toolRepository.save(tool);
    }

    /**
     * Delete a tool
     */
    public void deleteTool(Long id) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + id));
        toolRepository.delete(tool);
    }

    /**
     * Decrement tool quantity (called when a tool is borrowed)
     * IMPORTANT: This ensures inventory is updated when borrowed
     */
    public Tool decrementQuantity(Long id) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + id));

        if (tool.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Tool is out of stock");
        }

        tool.setAvailableQuantity(tool.getAvailableQuantity() - 1);
        return toolRepository.save(tool);
    }
}
