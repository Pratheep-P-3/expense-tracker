package com.expensetracker.entity;

import com.expensetracker.enums.CategoryApplicableTo;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicable_to", nullable = false)
    private CategoryApplicableTo applicableTo;

    // Constructors
    public Category() {
    }

    public Category(String name, CategoryApplicableTo applicableTo) {
        this.name = name;
        this.applicableTo = applicableTo;
    }

    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryApplicableTo getApplicableTo() {
        return applicableTo;
    }

    public void setApplicableTo(CategoryApplicableTo applicableTo) {
        this.applicableTo = applicableTo;
    }
}
