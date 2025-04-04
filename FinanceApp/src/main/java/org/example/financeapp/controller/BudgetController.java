package org.example.financeapp.controller;

import org.example.financeapp.model.Budget;
import org.example.financeapp.service.BudgetService;
import org.example.financeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @PostMapping("/{userId}")
    public String createOrUpdateBudget(
            @PathVariable Long userId,
            @ModelAttribute Budget budget
    ) {
        budgetService.saveBudget(userId, budget);
        return "redirect:/budgets/" + userId + "/view"; // Redirect to view
    }

    @GetMapping("/{userId}/{month}/{year}")
    public Budget getBudgetByMonthYear(
            @PathVariable Long userId,
            @PathVariable int month,
            @PathVariable int year
    ) {
        return budgetService.getBudgetByUserAndMonthYear(userId, month, year);
    }

    @GetMapping("/{userId}")
    public List<Budget> getAllBudgetsForUser(@PathVariable Long userId) {
        return budgetService.getAllBudgetsByUser(userId);
    }

    @GetMapping("/{userId}/view")
    public String getBudgetPage(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("budgets", budgetService.getAllBudgetsByUser(userId));
        return "budget";
    }

    @DeleteMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudget(@PathVariable Long budgetId) {
        budgetService.deleteBudget(budgetId);
    }
}