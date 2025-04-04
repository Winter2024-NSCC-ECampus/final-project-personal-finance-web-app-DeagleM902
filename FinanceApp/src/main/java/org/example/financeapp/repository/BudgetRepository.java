package org.example.financeapp.repository;

import org.example.financeapp.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUser_IdAndMonthAndYear(Long userId, int month, int year);

    List<Budget> findByUser_Id(Long userId);
}