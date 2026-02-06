package com.expensetracker.controller;

import com.expensetracker.dto.LoginRequestDTO;
import com.expensetracker.dto.UserRequestDTO;
import com.expensetracker.dto.UserResponseDTO;
import com.expensetracker.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint for user registration
     * POST /auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = authService.signup(userRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Endpoint for user login
     * POST /auth/login
     * Returns user details if credentials are valid
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO responseDTO = authService.login(loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
