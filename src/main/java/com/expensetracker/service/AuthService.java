package com.expensetracker.service;

import com.expensetracker.dto.LoginRequestDTO;
import com.expensetracker.dto.UserRequestDTO;
import com.expensetracker.dto.UserResponseDTO;

public interface AuthService {

    UserResponseDTO signup(UserRequestDTO userRequestDTO);

    UserResponseDTO login(LoginRequestDTO loginRequestDTO);
}
