package org.example.financeapp.service;

import org.example.financeapp.model.Spending;
import org.example.financeapp.model.User;
import org.example.financeapp.model.Category;
import org.example.financeapp.repository.SpendingRepository;
import org.example.financeapp.repository.UserRepository;
import org.example.financeapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SpendingService {
    private final SpendingRepository spendingRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public SpendingService(
            SpendingRepository spendingRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository
    ) {
        this.spendingRepository = spendingRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Record a new spending transaction
    @Transactional
    public Spending addSpending(Long userId, Long categoryId, Spending spending) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        spending.setUser(user);
        spending.setCategory(category);
        return spendingRepository.save(spending);
    }

    // Get all spendings for a user within a date range
    public List<Spending> getSpendingsByUserAndDateRange(Long userId, LocalDate start, LocalDate end) {
        return spendingRepository.findByUser_IdAndDateBetween(userId, start, end);
    }

    // Calculate total spending for a user in a category
    public double getTotalSpendingByCategory(Long userId, Long categoryId) {
        return spendingRepository.findByUser_IdAndCategory_Id(userId, categoryId)
                .stream()
                .mapToDouble(Spending::getAmount)
                .sum();
    }

    // Calculate total spending for a user (all categories)
    public double getTotalSpending(Long userId) {
        return spendingRepository.findByUser_Id(userId)
                .stream()
                .mapToDouble(Spending::getAmount)
                .sum();
    }

    public List<Spending> getRecentSpendings(Long userId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30); // Last 30 days
        return spendingRepository.findByUser_IdAndDateBetween(userId, startDate, endDate);
    }
}