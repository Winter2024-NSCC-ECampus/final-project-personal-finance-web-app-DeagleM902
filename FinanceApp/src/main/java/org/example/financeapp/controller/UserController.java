package org.example.financeapp.controller;

import org.example.financeapp.model.User;
import org.example.financeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user); // Add this line
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}