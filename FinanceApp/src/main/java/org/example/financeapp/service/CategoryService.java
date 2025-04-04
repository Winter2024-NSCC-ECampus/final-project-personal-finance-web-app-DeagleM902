package org.example.financeapp.service;

import org.example.financeapp.model.Category;
import org.example.financeapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all predefined categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get a category by ID (for validation)
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
    }
}