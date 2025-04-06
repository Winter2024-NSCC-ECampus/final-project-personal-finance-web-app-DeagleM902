package org.example.financeapp.controller;

import org.example.financeapp.model.Spending;
import org.example.financeapp.model.User;
import org.example.financeapp.service.CategoryService;
import org.example.financeapp.service.SpendingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/spendings")
public class SpendingController {
    private final SpendingService spendingService;
    private final CategoryService categoryService;

    public SpendingController(SpendingService spendingService, CategoryService categoryService) {
        this.spendingService = spendingService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showSpendingHistory(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                      Model model,
                                      @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.getAll());
        List<Spending> spendingHistory;
        if (categoryId != null) {
            spendingHistory = spendingService.getSpendingsByUserAndCategory(user.getId(), categoryId);
        } else {
            spendingHistory = spendingService.getAllSpendingsByUser(user.getId());
        }
        model.addAttribute("spendingHistory", spendingHistory);
        return "spending_history";
    }

    @PostMapping("/add")
    public String addSpending(
            @RequestParam Long userId,
            @RequestParam Long categoryId,
            @ModelAttribute Spending spending
    ) {
        spending.setDate(LocalDate.now());
        spendingService.addSpending(userId, categoryId, spending);
        return "redirect:/home";
    }

    @GetMapping("/add")
    public String showAddSpendingForm(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);          // From session/auth
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("spendings", spendingService.getRecent(user.getId()));
        return "spending";
    }
}