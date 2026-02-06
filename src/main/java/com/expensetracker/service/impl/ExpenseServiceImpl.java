package com.expensetracker.service.impl;

import com.expensetracker.dto.ExpenseRequestDTO;
import com.expensetracker.dto.ExpenseResponseDTO;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.enums.ExpenseType;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.exception.UserNotFoundException;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.service.ExpenseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, 
                             UserRepository userRepository,
                             CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO) {
        // Validate and fetch user
        User user = userRepository.findById(expenseRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + expenseRequestDTO.getUserId()));

        // Validate and fetch category
        Category category = categoryRepository.findById(expenseRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + expenseRequestDTO.getCategoryId()));

        // Create expense entity
        Expense expense = new Expense();
        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setExpenseDate(expenseRequestDTO.getExpenseDate());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setExpenseType(expenseRequestDTO.getExpenseType());
        expense.setUser(user);
        expense.setCategory(category);

        Expense savedExpense = expenseRepository.save(expense);

        return convertToResponseDTO(savedExpense);
    }

    @Override
    @Transactional
    public ExpenseResponseDTO updateExpense(Long expenseId, ExpenseRequestDTO expenseRequestDTO) {
        // Fetch existing expense
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with ID: " + expenseId));

        // Validate and fetch category if changed
        if (!expense.getCategory().getCategoryId().equals(expenseRequestDTO.getCategoryId())) {
            Category category = categoryRepository.findById(expenseRequestDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + expenseRequestDTO.getCategoryId()));
            expense.setCategory(category);
        }

        // Update expense fields
        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setExpenseDate(expenseRequestDTO.getExpenseDate());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setExpenseType(expenseRequestDTO.getExpenseType());

        Expense updatedExpense = expenseRepository.save(expense);

        return convertToResponseDTO(updatedExpense);
    }

    @Override
    @Transactional
    public void deleteExpense(Long expenseId) {
        // Check if expense exists
        if (!expenseRepository.existsById(expenseId)) {
            throw new ExpenseNotFoundException("Expense not found with ID: " + expenseId);
        }

        expenseRepository.deleteById(expenseId);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponseDTO getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with ID: " + expenseId));

        return convertToResponseDTO(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDTO> getAllExpenses(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserUserId(userId);
        return expenses.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDTO> getExpensesByType(Long userId, ExpenseType expenseType) {
        List<Expense> expenses = expenseRepository.findByUserUserIdAndExpenseType(userId, expenseType);
        return expenses.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDTO> getExpensesByFilters(
            Long userId,
            ExpenseType expenseType,
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId) {
        
        // Use repository method that handles null parameters
        List<Expense> expenses = expenseRepository.findByFilters(
                userId, expenseType, startDate, endDate, categoryId
        );

        return expenses.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to convert Expense entity to ExpenseResponseDTO
     */
    private ExpenseResponseDTO convertToResponseDTO(Expense expense) {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        responseDTO.setExpenseId(expense.getExpenseId());
        responseDTO.setAmount(expense.getAmount());
        responseDTO.setExpenseDate(expense.getExpenseDate());
        responseDTO.setDescription(expense.getDescription());
        responseDTO.setExpenseType(expense.getExpenseType());
        responseDTO.setCreatedAt(expense.getCreatedAt());
        responseDTO.setUserId(expense.getUser().getUserId());
        responseDTO.setUsername(expense.getUser().getUsername());
        responseDTO.setCategoryId(expense.getCategory().getCategoryId());
        responseDTO.setCategoryName(expense.getCategory().getName());
        return responseDTO;
    }
}
