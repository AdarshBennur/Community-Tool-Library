package com.library.borrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDTO - Data Transfer Object for User information
 * 
 * Used to receive user data from User Service via Feign Client.
 * Must match the User entity structure in User Service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
