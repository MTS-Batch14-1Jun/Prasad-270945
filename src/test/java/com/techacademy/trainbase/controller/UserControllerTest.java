package com.techacademy.trainbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techacademy.trainbase.dto.UserCreateDTO;
import com.techacademy.trainbase.dto.UserDTO;
import com.techacademy.trainbase.dto.UserUpdateDTO;
import com.techacademy.trainbase.entity.User;
import com.techacademy.trainbase.mapper.UserMapper;
import com.techacademy.trainbase.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
@DisplayName("UserController Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    @SuppressWarnings("all")
    private UserService userService;

    @MockitoBean
    @SuppressWarnings("all")
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private UserDTO testUserDTO;
    private UserCreateDTO userCreateDTO;
    private UserUpdateDTO userUpdateDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("john_doe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("password123");
        testUser.setRole("USER");
        testUser.setCreatedAt(LocalDateTime.now());

        testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        testUserDTO.setUsername("john_doe");
        testUserDTO.setEmail("john@example.com");
        testUserDTO.setRole("USER");

        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("new_user");
        userCreateDTO.setEmail("new@example.com");
        userCreateDTO.setPassword("password123");
        userCreateDTO.setRole("USER");

        userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUsername("updated_user");
        userUpdateDTO.setEmail("updated@example.com");
        userUpdateDTO.setPassword("newpassword123");
        userUpdateDTO.setRole("ADMIN");
    }

    // ======================== GET /api/users ========================
    @Test
    @DisplayName("Get all users - Success")
    void testGetAllUsers_Success() throws Exception {
        // Arrange
        List<User> users = List.of(testUser);
        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].username").value("john_doe"))
                .andExpect(jsonPath("$.data[0].email").value("john@example.com"));

        verify(userService, times(1)).getAllUsers();
        verify(userMapper, times(1)).toDTO(testUser);
    }

    @Test
    @DisplayName("Get all users - Empty list")
    void testGetAllUsers_EmptyList() throws Exception {
        // Arrange
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(0)));

        verify(userService, times(1)).getAllUsers();
    }

    // ======================== GET /api/users/{id} ========================
    @Test
    @DisplayName("Get user by ID - Success")
    void testGetUserById_Success() throws Exception {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("john_doe"));

        verify(userService, times(1)).getUserById(1L);
        verify(userMapper, times(1)).toDTO(testUser);
    }

    @Test
    @DisplayName("Get user by ID - Not found")
    void testGetUserById_NotFound() throws Exception {
        // Arrange
        when(userService.getUserById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(999L);
    }

    // ======================== POST /api/users ========================
    @Test
    @DisplayName("Create user - Success")
    void testCreateUser_Success() throws Exception {
        // Arrange
        User newUser = new User();
        newUser.setUsername(userCreateDTO.getUsername());
        newUser.setEmail(userCreateDTO.getEmail());
        newUser.setPassword(userCreateDTO.getPassword());
        newUser.setRole(userCreateDTO.getRole());

        User createdUser = new User();
        createdUser.setId(2L);
        createdUser.setUsername(userCreateDTO.getUsername());
        createdUser.setEmail(userCreateDTO.getEmail());
        createdUser.setPassword(userCreateDTO.getPassword());
        createdUser.setRole(userCreateDTO.getRole());

        UserDTO createdUserDTO = new UserDTO();
        createdUserDTO.setId(2L);
        createdUserDTO.setUsername(userCreateDTO.getUsername());
        createdUserDTO.setEmail(userCreateDTO.getEmail());
        createdUserDTO.setRole(userCreateDTO.getRole());

        when(userMapper.toEntity(userCreateDTO)).thenReturn(newUser);
        when(userService.createUser(any(User.class))).thenReturn(createdUser);
        when(userMapper.toDTO(createdUser)).thenReturn(createdUserDTO);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User created successfully"));

    }

    @Test
    @DisplayName("Create user - Invalid username (blank)")
    void testCreateUser_InvalidUsername_Blank() throws Exception {
        // Arrange
        UserCreateDTO invalidDTO = new UserCreateDTO();
        invalidDTO.setUsername("");
        invalidDTO.setEmail("test@example.com");
        invalidDTO.setPassword("password123");
        invalidDTO.setRole("USER");

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any());
    }

    @Test
    @DisplayName("Create user - Invalid email format")
    void testCreateUser_InvalidEmailFormat() throws Exception {
        // Arrange
        UserCreateDTO invalidDTO = new UserCreateDTO();
        invalidDTO.setUsername("test_user");
        invalidDTO.setEmail("invalid-email");
        invalidDTO.setPassword("password123");
        invalidDTO.setRole("USER");

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any());
    }

    @Test
    @DisplayName("Create user - Password too short")
    void testCreateUser_PasswordTooShort() throws Exception {
        // Arrange
        UserCreateDTO invalidDTO = new UserCreateDTO();
        invalidDTO.setUsername("test_user");
        invalidDTO.setEmail("test@example.com");
        invalidDTO.setPassword("short");
        invalidDTO.setRole("USER");

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any());
    }

    // ======================== PUT /api/users/{id} ========================
    @Test
    @DisplayName("Update user - Success")
    void testUpdateUser_Success() throws Exception {
        // Arrange
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("john_doe");
        existingUser.setEmail("john@example.com");
        existingUser.setPassword("password123");
        existingUser.setRole("USER");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("updated_user");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("newpassword123");
        updatedUser.setRole("ADMIN");

        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(1L);
        updatedUserDTO.setUsername("updated_user");
        updatedUserDTO.setEmail("updated@example.com");
        updatedUserDTO.setRole("ADMIN");

        when(userService.getUserById(1L)).thenReturn(Optional.of(existingUser));
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);
        when(userMapper.toDTO(updatedUser)).thenReturn(updatedUserDTO);

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("updated_user"))
                .andExpect(jsonPath("$.data.role").value("ADMIN"));

        verify(userService, times(1)).getUserById(1L);
        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
        verify(userMapper, times(1)).toDTO(updatedUser);
    }

    @Test
    @DisplayName("Update user - User not found")
    void testUpdateUser_UserNotFound() throws Exception {
        // Arrange
        when(userService.getUserById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(999L);
        verify(userService, never()).updateUser(any(), any());
    }

    // ======================== DELETE /api/users/{id} ========================
    @Test
    @DisplayName("Delete user - Success")
    void testDeleteUser_Success() throws Exception {
        // Arrange
        when(userService.deleteUser(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User deleted successfully"));

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    @DisplayName("Delete user - User not found")
    void testDeleteUser_UserNotFound() throws Exception {
        // Arrange
        when(userService.deleteUser(999L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteUser(999L);
    }

    // ======================== GET /api/users/username/{username} ========================
    @Test
    @DisplayName("Get user by username - Success")
    void testGetUserByUsername_Success() throws Exception {
        // Arrange
        when(userService.getUserByUsername("john_doe")).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users/username/john_doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("john_doe"));

        verify(userService, times(1)).getUserByUsername("john_doe");
        verify(userMapper, times(1)).toDTO(testUser);
    }

    @Test
    @DisplayName("Get user by username - Not found")
    void testGetUserByUsername_NotFound() throws Exception {
        // Arrange
        when(userService.getUserByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/users/username/nonexistent")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserByUsername("nonexistent");
    }

    // ======================== GET /api/users/email/{email} ========================
    @Test
    @DisplayName("Get user by email - Success")
    void testGetUserByEmail_Success() throws Exception {
        // Arrange
        when(userService.getUserByEmail("john@example.com")).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users/email/john@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));

        verify(userService, times(1)).getUserByEmail("john@example.com");
        verify(userMapper, times(1)).toDTO(testUser);
    }

    @Test
    @DisplayName("Get user by email - Not found")
    void testGetUserByEmail_NotFound() throws Exception {
        // Arrange
        when(userService.getUserByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/users/email/nonexistent@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserByEmail("nonexistent@example.com");
    }

    // ======================== GET /api/users/exists/username/{username} ========================
    @Test
    @DisplayName("Check if username exists - True")
    void testExistsByUsername_True() throws Exception {
        // Arrange
        when(userService.existsByUsername("john_doe")).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/users/exists/username/john_doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));

        verify(userService, times(1)).existsByUsername("john_doe");
    }

    @Test
    @DisplayName("Check if username exists - False")
    void testExistsByUsername_False() throws Exception {
        // Arrange
        when(userService.existsByUsername("nonexistent")).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/api/users/exists/username/nonexistent")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(false));

        verify(userService, times(1)).existsByUsername("nonexistent");
    }

    // ======================== GET /api/users/exists/email/{email} ========================
    @Test
    @DisplayName("Check if email exists - True")
    void testExistsByEmail_True() throws Exception {
        // Arrange
        when(userService.existsByEmail("john@example.com")).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/users/exists/email/john@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));

        verify(userService, times(1)).existsByEmail("john@example.com");
    }

    @Test
    @DisplayName("Check if email exists - False")
    void testExistsByEmail_False() throws Exception {
        // Arrange
        when(userService.existsByEmail("nonexistent@example.com")).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/api/users/exists/email/nonexistent@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(false));

        verify(userService, times(1)).existsByEmail("nonexistent@example.com");
    }

    // ======================== Integration Tests ========================
    @Test
    @DisplayName("Create multiple users and verify all fetch successfully")
    void testCreateAndFetchMultipleUsers() throws Exception {
        // Create first user
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        user1.setRole("USER");

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(1L);
        userDTO1.setUsername("user1");
        userDTO1.setEmail("user1@example.com");
        userDTO1.setRole("USER");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userMapper.toDTO(user1)).thenReturn(userDTO1);

        // Act & Assert - Fetch created user
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("user1"));

        verify(userService, times(1)).getUserById(1L);
    }
}