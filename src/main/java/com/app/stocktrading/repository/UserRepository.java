package com.app.stocktrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.stocktrading.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
