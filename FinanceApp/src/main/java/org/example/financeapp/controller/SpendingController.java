package org.example.financeapp.controller;

import org.example.financeapp.model.Spending;
import org.example.financeapp.service.CategoryService;
import org.example.financeapp.service.SpendingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/add")
    public String addSpending(
            @RequestParam Long userId,
            @RequestParam Long categoryId,
            @ModelAttribute Spending spending
    ) {
        spendingService.addSpending(userId, categoryId, spending);
        return "redirect:/home";
    }

    @GetMapping("/{userId}")
    public String getSpendingsByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model
    ) {
        List<Spending> spendings = spendingService.getSpendingsByUserAndDateRange(userId, start, end);
        model.addAttribute("spendings", spendings);
        return "spending";
    }

    @GetMapping("/{userId}/total")
    public double getTotalSpending(@PathVariable Long userId) {
        return spendingService.getTotalSpending(userId);
    }

    @GetMapping("/{userId}/category/{categoryId}/total")
    public double getCategorySpendingTotal(
            @PathVariable Long userId,
            @PathVariable Long categoryId
    ) {
        return spendingService.getTotalSpendingByCategory(userId, categoryId);
    }

    @GetMapping("/add")
    public String showAddSpendingForm(
            @RequestParam Long userId,  // Get from session or model
            Model model
    ) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("spending", new Spending());
        return "spending";
    }
}