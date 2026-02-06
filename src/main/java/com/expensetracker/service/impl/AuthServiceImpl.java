package com.expensetracker.service.impl;

import com.expensetracker.dto.LoginRequestDTO;
import com.expensetracker.dto.UserRequestDTO;
import com.expensetracker.dto.UserResponseDTO;
import com.expensetracker.entity.User;
import com.expensetracker.exception.InvalidCredentialsException;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponseDTO signup(UserRequestDTO userRequestDTO) {
        // Check if username already exists
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create new user - password stored as plain text (intentional)
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());

        User savedUser = userRepository.save(user);

        return convertToResponseDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // Find user by username
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        // Check password (plain text comparison - intentional)
        if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return convertToResponseDTO(user);
    }

    /**
     * Helper method to convert User entity to UserResponseDTO
     */
    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setCreatedAt(user.getCreatedAt());
        return responseDTO;
    }
}
