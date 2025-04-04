package org.example.financeapp.repository;

import org.example.financeapp.model.Spending;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface SpendingRepository extends JpaRepository<Spending, Long> {
    List<Spending> findByUser_IdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    List<Spending> findByUser_Id(Long userId);

    List<Spending> findByUser_IdAndCategory_Id(Long userId, Long categoryId);
}