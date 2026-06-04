package com.techacademy.trainbase.controller;

import com.techacademy.trainbase.dto.UserCreateDTO;
import com.techacademy.trainbase.dto.UserDTO;
import com.techacademy.trainbase.dto.UserUpdateDTO;
import com.techacademy.trainbase.entity.User;
import com.techacademy.trainbase.exception.ResourceNotFoundException;
import com.techacademy.trainbase.mapper.UserMapper;
import com.techacademy.trainbase.response.ApiResponse;
import com.techacademy.trainbase.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * Retrieve all users from the system
     *
     * This endpoint fetches a list of all users registered in the system.
     * Each user is mapped to UserDTO to exclude sensitive information like password.
     *
     * HTTP Method: GET
     * URL: /api/users
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true
     *         - data: List of UserDTO objects
     *         - HTTP Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        // Fetch all users from database and map them to DTOs
        List<UserDTO> users = userService.getAllUsers()
            .stream()
            .map(userMapper::toDTO)
            .collect(Collectors.toList());

        // Return wrapped in ApiResponse success wrapper
        return ResponseEntity.ok(ApiResponse.success(users));
    }
    
    /**
     * Retrieve a specific user by their ID
     *
     * HTTP Method: GET
     * URL: /api/users/{id}
     *
     * @param id the unique identifier of the user (path variable)
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true if user found
     *         - data: UserDTO object with user details
     *         - HTTP Status: 200 OK
     *
     * @throws ResourceNotFoundException if user with given ID is not found
     *         Returns HTTP 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        // Fetch user by ID, throw exception if not found
        User user = userService.getUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Map entity to DTO and return wrapped response
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(user)));
    }
    
    /**
     * Create a new user in the system
     *
     * This endpoint creates a new user with the provided details.
     * Password is hashed before storage (recommended: implement in service layer).
     * Email and username must be unique.
     *
     * HTTP Method: POST
     * URL: /api/users
     *
     * @param userCreateDTO the user creation request containing:
     *                      - username: required, 3-50 characters
     *                      - email: required, valid email format
     *                      - password: required, min 6 characters
     *                      - role: required (USER, ADMIN, etc.)
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true
     *         - message: "User created successfully"
     *         - data: Created UserDTO object with assigned ID
     *         - HTTP Status: 201 Created
     *
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     *         Returns HTTP 400 Bad Request
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        // Map DTO to entity
        User user = userMapper.toEntity(userCreateDTO);

        // Save to database via service layer
        User createdUser = userService.createUser(user);

        // Return 201 Created with user data
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("User created successfully", userMapper.toDTO(createdUser)));
    }
    
    /**
     * Update an existing user
     *
     * This endpoint updates user information for the given ID.
     * Only fields provided in the request will be updated.
     *
     * HTTP Method: PUT
     * URL: /api/users/{id}
     *
     * @param id the ID of the user to update (path variable)
     * @param userUpdateDTO the update request containing:
     *                      - username: optional
     *                      - email: optional
     *                      - password: optional
     *                      - role: optional
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true
     *         - message: "User updated successfully"
     *         - data: Updated UserDTO object
     *         - HTTP Status: 200 OK
     *
     * @throws ResourceNotFoundException if user not found
     *         Returns HTTP 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        // Check if user exists, throw 404 if not
        User existingUser = userService.getUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Update entity from DTO (only non-null fields)
        userMapper.updateEntityFromDTO(userUpdateDTO, existingUser);

        // Save updated entity
        User updatedUser = userService.updateUser(id, existingUser);
        
        // Return updated user data
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", userMapper.toDTO(updatedUser)));
    }
    
    /**
     * Delete a user by ID
     *
     * This endpoint permanently removes a user from the system.
     * The action is irreversible.
     *
     * HTTP Method: DELETE
     * URL: /api/users/{id}
     *
     * @param id the ID of the user to delete (path variable)
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true
     *         - message: "User deleted successfully"
     *         - HTTP Status: 200 OK
     *
     * @throws ResourceNotFoundException if user not found
     *         Returns HTTP 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        // Attempt to delete user
        boolean deleted = userService.deleteUser(id);

        // Return success if deleted, throw 404 if not found
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
        }
        throw new ResourceNotFoundException("User", "id", id);
    }
    
    /**
     * Get a user by username
     *
     * This endpoint searches for a user with the given username and returns their details.
     *
     * HTTP Method: GET
     * URL: /api/users/username/{username}
     *
     * @param username the username to search for (path variable)
     *                 Must be between 3 and 50 characters
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true if user found
     *         - data: UserDTO object
     *         - HTTP Status: 200 OK
     *
     * @throws ResourceNotFoundException if user not found
     *         Returns HTTP 404 Not Found
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUsername(@PathVariable String username) {
        // Search for user by username
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Return user data
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(user)));
    }
    
    /**
     * Get a user by email address
     *
     * This endpoint searches for a user with the given email and returns their details.
     *
     * HTTP Method: GET
     * URL: /api/users/email/{email}
     *
     * @param email the email address to search for (path variable)
     *              Must be a valid email format
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true if user found
     *         - data: UserDTO object
     *         - HTTP Status: 200 OK
     *
     * @throws ResourceNotFoundException if user not found
     *         Returns HTTP 404 Not Found
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        // Search for user by email
        User user = userService.getUserByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        // Return user data
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(user)));
    }
    
    /**
     * Check if a user exists by username
     *
     * This endpoint allows clients to verify if a user with the given username exists
     * in the system without retrieving the full user details. It's useful for:
     * - Username validation during registration
     * - Checking username availability
     * - User existence verification
     *
     * HTTP Method: GET
     * URL: /api/users/exists/username/{username}
     *
     * @param username the username to check for existence (path variable)
     *                 Must be between 3 and 50 characters
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true (operation successful)
     *         - data: true if user exists, false if user does not exist
     *         - HTTP Status: 200 OK
     *
     * @example
     *         GET /api/users/exists/username/john_doe
     *         Response: {
     *              "success": true,
     *              "data": true,
     *              "timestamp": "2026-06-04T10:30:00"
     *         }
     *
     * @see UserService#existsByUsername(String)
     * @see ApiResponse
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<ApiResponse<Boolean>> existsByUsername(@PathVariable String username) {
        // Call service layer to check if user with this username exists
        boolean exists = userService.existsByUsername(username);

        // Return success response with boolean result
        // True: User with this username exists
        // False: No user found with this username
        return ResponseEntity.ok(ApiResponse.success(exists));
    }
    
    /**
     * Check if a user exists by email address
     *
     * This endpoint allows clients to verify if a user with the given email exists
     * in the system without retrieving the full user details. It's useful for:
     * - Email validation during registration
     * - Checking email availability before user creation
     * - User existence verification
     *
     * HTTP Method: GET
     * URL: /api/users/exists/email/{email}
     *
     * @param email the email address to check for existence (path variable)
     *              Must be a valid email format (though validation happens in UserCreateDTO)
     *
     * @return ResponseEntity containing ApiResponse with:
     *         - success: true (operation successful)
     *         - data: true if user exists, false if user does not exist
     *         - HTTP Status: 200 OK
     *
     * @example
     *         GET /api/users/exists/email/john@example.com
     *         Response: {
     *              "success": true,
     *              "data": true,
     *              "timestamp": "2026-06-04T10:30:00"
     *         }
     *
     * @see UserService#existsByEmail(String)
     * @see ApiResponse
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> existsByEmail(@PathVariable String email) {
        // Call service layer to check if user with this email exists
        boolean exists = userService.existsByEmail(email);

        // Return success response with boolean result
        // True: User with this email exists
        // False: No user found with this email
        return ResponseEntity.ok(ApiResponse.success(exists));
    }
}
