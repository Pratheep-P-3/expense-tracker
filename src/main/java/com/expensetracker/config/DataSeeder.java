package com.expensetracker.config;

import com.expensetracker.entity.Category;
import com.expensetracker.enums.CategoryApplicableTo;
import com.expensetracker.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder {

    /**
     * Seeds sample categories on application startup
     * Only adds categories if they don't already exist
     */
    @Bean
    public CommandLineRunner seedCategories(CategoryRepository categoryRepository) {
        return args -> {
            // Check if categories already exist
            if (categoryRepository.count() == 0) {
                List<Category> categories = Arrays.asList(
                        // Personal categories
                        new Category("Groceries", CategoryApplicableTo.PERSONAL),
                        new Category("Transportation", CategoryApplicableTo.PERSONAL),
                        new Category("Healthcare", CategoryApplicableTo.PERSONAL),
                        new Category("Entertainment", CategoryApplicableTo.PERSONAL),
                        new Category("Utilities", CategoryApplicableTo.BOTH),
                        new Category("Rent", CategoryApplicableTo.BOTH),
                        new Category("Education", CategoryApplicableTo.BOTH),
                        
                        // Organizational categories
                        new Category("Office Supplies", CategoryApplicableTo.ORGANIZATIONAL),
                        new Category("Travel", CategoryApplicableTo.BOTH),
                        new Category("Marketing", CategoryApplicableTo.ORGANIZATIONAL),
                        new Category("Software & Subscriptions", CategoryApplicableTo.ORGANIZATIONAL),
                        new Category("Equipment", CategoryApplicableTo.ORGANIZATIONAL),
                        new Category("Client Entertainment", CategoryApplicableTo.ORGANIZATIONAL),
                        new Category("Professional Services", CategoryApplicableTo.ORGANIZATIONAL),
                        
                        // General categories
                        new Category("Miscellaneous", CategoryApplicableTo.BOTH),
                        new Category("Insurance", CategoryApplicableTo.BOTH)
                );

                categoryRepository.saveAll(categories);
                System.out.println("✓ Sample categories seeded successfully");
            } else {
                System.out.println("✓ Categories already exist, skipping seed");
            }
        };
    }
}
