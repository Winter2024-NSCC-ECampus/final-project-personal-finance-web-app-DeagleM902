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

    @Transactional
    public Budget saveBudget(Long userId, Budget budget) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        budget.setUser(user);
        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgetsByUser(Long userId) {
        return budgetRepository.findByUser_Id(userId);
    }

    public Budget getCurrentBudget(Long userId) {
        LocalDate now = LocalDate.now();
        return budgetRepository.findByUser_IdAndMonthAndYear(
                userId,
                now.getMonthValue(),
                now.getYear()
        ).orElse(null);
    }

}