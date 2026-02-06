package com.expensetracker.dto;

import com.expensetracker.enums.ExpenseType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExpenseResponseDTO {

    private Long expenseId;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String description;
    private ExpenseType expenseType;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
    private Long categoryId;
    private String categoryName;

    // Constructors
    public ExpenseResponseDTO() {
    }

    public ExpenseResponseDTO(Long expenseId, BigDecimal amount, LocalDate expenseDate, 
                             String description, ExpenseType expenseType, LocalDateTime createdAt,
                             Long userId, String username, Long categoryId, String categoryName) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.description = description;
        this.expenseType = expenseType;
        this.createdAt = createdAt;
        this.userId = userId;
        this.username = username;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
