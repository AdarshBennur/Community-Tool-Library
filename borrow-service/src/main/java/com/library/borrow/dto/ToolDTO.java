package com.library.borrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ToolDTO - Data Transfer Object for Tool information
 * 
 * Used to receive tool data from Tool Service via Feign Client.
 * Must match the Tool entity structure in Tool Service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolDTO {
    private Long id;
    private String name;
    private String category;
    private Integer availableQuantity;
}
