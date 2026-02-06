package com.expensetracker.controller;

import com.expensetracker.dto.ExpenseRequestDTO;
import com.expensetracker.dto.ExpenseResponseDTO;
import com.expensetracker.enums.ExpenseType;
import com.expensetracker.service.ExpenseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Create a new expense
     * POST /expenses
     */
    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) {
        ExpenseResponseDTO responseDTO = expenseService.createExpense(expenseRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Update an existing expense
     * PUT /expenses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable("id") Long expenseId,
            @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        ExpenseResponseDTO responseDTO = expenseService.updateExpense(expenseId, expenseRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Delete an expense
     * DELETE /expenses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get expense by ID
     * GET /expenses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable("id") Long expenseId) {
        ExpenseResponseDTO responseDTO = expenseService.getExpenseById(expenseId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Get all expenses with optional filters
     * GET /expenses
     * GET /expenses?userId=1
     * GET /expenses?userId=1&type=PERSONAL
     * GET /expenses?userId=1&type=ORGANIZATIONAL
     * GET /expenses?userId=1&startDate=2025-01-01&endDate=2025-12-31
     * GET /expenses?userId=1&categoryId=2
     * GET /expenses?userId=1&type=PERSONAL&startDate=2025-01-01&endDate=2025-12-31&categoryId=2
     */
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenses(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "type", required = false) ExpenseType type,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "categoryId", required = false) Long categoryId) {

        List<ExpenseResponseDTO> expenses;

        // If any filter is provided, use the filter method
        if (type != null || startDate != null || endDate != null || categoryId != null) {
            expenses = expenseService.getExpensesByFilters(userId, type, startDate, endDate, categoryId);
        } else {
            // No filters, return all expenses for the user
            expenses = expenseService.getAllExpenses(userId);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
}
