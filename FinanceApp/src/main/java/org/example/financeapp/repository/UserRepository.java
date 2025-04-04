package org.example.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.financeapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
