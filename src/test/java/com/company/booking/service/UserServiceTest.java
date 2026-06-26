package com.company.booking.service;

import com.company.booking.entity.User;
import com.company.booking.entity.Role;
import com.company.booking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setFullName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setRole(Role.USER);
        testUser.setEnabled(true);
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.register(testUser);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameExists_ThrowsException() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.register(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAuthenticate_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "password123")).thenReturn(true);

        Optional<User> result = userService.authenticate("testuser", "password123");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void testAuthenticate_WrongPassword_ReturnsEmpty() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", "password123")).thenReturn(false);

        Optional<User> result = userService.authenticate("testuser", "wrongpassword");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }
}
