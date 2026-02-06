package com.expensetracker.repository;

import com.expensetracker.entity.Expense;
import com.expensetracker.enums.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Find all expenses by user
    List<Expense> findByUserUserId(Long userId);

    // Find expenses by user and expense type
    List<Expense> findByUserUserIdAndExpenseType(Long userId, ExpenseType expenseType);

    // Find expenses by user and date range
    @Query("SELECT e FROM Expense e WHERE e.user.userId = :userId " +
           "AND e.expenseDate BETWEEN :startDate AND :endDate")
    List<Expense> findByUserAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Find expenses by user and category
    List<Expense> findByUserUserIdAndCategoryCategoryId(Long userId, Long categoryId);

    // Complex filter: user, type, date range, and category
    @Query("SELECT e FROM Expense e WHERE e.user.userId = :userId " +
           "AND (:expenseType IS NULL OR e.expenseType = :expenseType) " +
           "AND (:startDate IS NULL OR e.expenseDate >= :startDate) " +
           "AND (:endDate IS NULL OR e.expenseDate <= :endDate) " +
           "AND (:categoryId IS NULL OR e.category.categoryId = :categoryId)")
    List<Expense> findByFilters(
            @Param("userId") Long userId,
            @Param("expenseType") ExpenseType expenseType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("categoryId") Long categoryId
    );
}
