package com.expensetracker.service;

import com.expensetracker.dto.ExpenseRequestDTO;
import com.expensetracker.dto.ExpenseResponseDTO;
import com.expensetracker.enums.ExpenseType;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO);

    ExpenseResponseDTO updateExpense(Long expenseId, ExpenseRequestDTO expenseRequestDTO);

    void deleteExpense(Long expenseId);

    ExpenseResponseDTO getExpenseById(Long expenseId);

    List<ExpenseResponseDTO> getAllExpenses(Long userId);

    List<ExpenseResponseDTO> getExpensesByType(Long userId, ExpenseType expenseType);

    List<ExpenseResponseDTO> getExpensesByFilters(
            Long userId,
            ExpenseType expenseType,
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId
    );
}
