package org.example.financeapp.controller;

import org.example.financeapp.model.Budget;
import org.example.financeapp.model.Spending;
import org.example.financeapp.model.User;
import org.example.financeapp.service.BudgetService;
import org.example.financeapp.service.SpendingService;
import org.example.financeapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final BudgetService budgetService;
    private final SpendingService spendingService;
    private final UserService userService;

    public HomeController(BudgetService budgetService, SpendingService spendingService, UserService userService) {
        this.budgetService = budgetService;
        this.spendingService = spendingService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Budget currentBudget = budgetService.getCurrentBudget(user.getId());
        List<Spending> recentSpendings = spendingService.getRecentSpendings(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("currentBudget", currentBudget);
        model.addAttribute("recentSpendings", recentSpendings);
        return "home";
    }
}