package org.example.financeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "budget", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "month", "year"}))
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double income;
    private int month;
    private int year;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    @NotEmpty
    private List<BudgetCategory> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @NotEmpty List<BudgetCategory> getCategories() {
        return categories;
    }

    public void setCategories(@NotEmpty List<BudgetCategory> categories) {
        this.categories = categories;
    }

    public double getTotalRemaining() {
        double totalAllocated = 0.0;
        if (categories != null) {
            totalAllocated = categories.stream()
                    .mapToDouble(BudgetCategory::getAmount)
                    .sum();
        }
        return income - totalAllocated;
    }
}
