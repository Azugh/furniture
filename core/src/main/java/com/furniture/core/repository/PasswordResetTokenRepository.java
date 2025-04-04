package com.furniture.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  PasswordResetToken findByToken(String token);
}
