package com.library.tool.controller;

import com.library.tool.model.Tool;
import com.library.tool.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Tool Controller - REST API endpoints
 * 
 * Handles HTTP requests for tool operations.
 * All endpoints are mapped to /tools
 */
@RestController
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private ToolService toolService;

    /**
     * POST /tools - Create a new tool
     */
    @PostMapping
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
        Tool createdTool = toolService.createTool(tool);
        return new ResponseEntity<>(createdTool, HttpStatus.CREATED);
    }

    /**
     * GET /tools/{id} - Get tool by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable Long id) {
        return toolService.getToolById(id)
                .map(tool -> new ResponseEntity<>(tool, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET /tools - Get all tools
     */
    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        List<Tool> tools = toolService.getAllTools();
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }

    /**
     * PUT /tools/{id} - Update an existing tool
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateTool(@PathVariable Long id, @RequestBody Tool tool) {
        try {
            Tool updatedTool = toolService.updateTool(id, tool);
            return new ResponseEntity<>(updatedTool, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE /tools/{id} - Delete a tool
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        try {
            toolService.deleteTool(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT /tools/{id}/decrement - Decrement tool quantity (called when borrowed)
     * This is used by Borrow Service to reduce available quantity
     */
    @PutMapping("/{id}/decrement")
    public ResponseEntity<Tool> decrementToolQuantity(@PathVariable Long id) {
        try {
            Tool updatedTool = toolService.decrementQuantity(id);
            return new ResponseEntity<>(updatedTool, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
