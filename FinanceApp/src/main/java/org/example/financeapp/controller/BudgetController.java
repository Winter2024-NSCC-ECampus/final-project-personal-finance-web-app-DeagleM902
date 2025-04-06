package org.example.financeapp.controller;

import jakarta.validation.Valid;
import org.example.financeapp.model.Budget;
import org.example.financeapp.model.BudgetCategory;
import org.example.financeapp.model.Category;
import org.example.financeapp.model.User;
import org.example.financeapp.service.BudgetService;
import org.example.financeapp.service.CategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;
    private final CategoryService categoryService;

    public BudgetController(BudgetService budgetService, CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showBudgetHistory(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        List<Budget> budgetHistory = budgetService.getAllBudgetsByUser(user.getId());
        model.addAttribute("budgetHistory", budgetHistory);
        return "budget_history";
    }

    @GetMapping("/add")
    public String showAddBudgetForm(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user", user);
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("categories", allCategories);

        Budget budget = new Budget();
        List<BudgetCategory> budgetCategories = new ArrayList<>();
        for (Category cat : allCategories) {
            BudgetCategory bc = new BudgetCategory();
            bc.setCategory(cat);
            budgetCategories.add(bc);
        }
        budget.setCategories(budgetCategories);
        model.addAttribute("budget", budget);
        return "budget";
    }

    @PostMapping("/save")
    public String saveBudget(
            @Valid @ModelAttribute("budget") Budget budget,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal User currentUser) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "budget";
        }

        if (budget.getCategories() != null) {
            budget.getCategories().forEach(budgetCategory -> budgetCategory.setBudget(budget));
        }
        budgetService.saveBudget(currentUser.getId(), budget);
        return "/home";
    }
}