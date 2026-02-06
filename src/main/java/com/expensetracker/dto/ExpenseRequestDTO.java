package com.expensetracker.dto;

import com.expensetracker.enums.ExpenseType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequestDTO {

    private BigDecimal amount;
    private LocalDate expenseDate;
    private String description;
    private ExpenseType expenseType;
    private Long userId;
    private Long categoryId;

    // Constructors
    public ExpenseRequestDTO() {
    }

    public ExpenseRequestDTO(BigDecimal amount, LocalDate expenseDate, String description, 
                            ExpenseType expenseType, Long userId, Long categoryId) {
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.description = description;
        this.expenseType = expenseType;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    // Getters and Setters
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
