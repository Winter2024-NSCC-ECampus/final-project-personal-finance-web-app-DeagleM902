package org.example.financeapp.service;

import org.example.financeapp.model.Budget;
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

    public List<Spending> getRecent(Long userId) {
        return spendingRepository.findTop10ByUser_IdOrderByDateDesc(userId);
    }

    public List<Spending> getAllSpendingsByUser(Long userId) {return spendingRepository.findByUser_Id(userId);}

    public List<Spending> getSpendingsByUserAndCategory(Long userId, Long categoryId) {
        return spendingRepository.findByUserIdAndCategoryId(userId, categoryId);
    }
}