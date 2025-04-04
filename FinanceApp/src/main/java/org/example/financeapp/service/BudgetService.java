package org.example.financeapp.service;

import org.example.financeapp.model.Budget;
import org.example.financeapp.model.User;
import org.example.financeapp.repository.BudgetRepository;
import org.example.financeapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    // Create/Update a budget for a user
    @Transactional
    public Budget saveBudget(Long userId, Budget budget) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        budget.setUser(user);
        return budgetRepository.save(budget);
    }

    // Get a user's budget for a specific month/year
    public Budget getBudgetByUserAndMonthYear(Long userId, int month, int year) {
        return budgetRepository.findByUser_IdAndMonthAndYear(userId, month, year)
                .orElseThrow(() -> new NoSuchElementException("Budget not found"));
    }

    // Get all budgets for a user
    public List<Budget> getAllBudgetsByUser(Long userId) {
        return budgetRepository.findByUser_Id(userId);
    }

    // Delete a budget
    public void deleteBudget(Long budgetId) {
        budgetRepository.deleteById(budgetId);
    }

    public Budget getCurrentBudget(Long userId) {
        LocalDate now = LocalDate.now();
        return budgetRepository.findByUser_IdAndMonthAndYear(
                userId,
                now.getMonthValue(), // Current month (1-12)
                now.getYear()        // Current year
        ).orElse(null); // Return null if no budget exists for current month
    }
}