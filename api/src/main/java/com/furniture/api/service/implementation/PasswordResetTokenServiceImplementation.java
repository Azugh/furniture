package com.furniture.api.service.implementation;

import org.springframework.stereotype.Service;

import com.furniture.api.service.PasswordResetTokenService;
import com.furniture.core.model.PasswordResetToken;
import com.furniture.core.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenServiceImplementation implements PasswordResetTokenService {
  private PasswordResetTokenRepository passwordResetTokenRepository;

  public PasswordResetTokenServiceImplementation(PasswordResetTokenRepository passwordResetTokenRepository) {
    this.passwordResetTokenRepository = passwordResetTokenRepository;
  }

  @Override
  public PasswordResetToken findByToken(String token) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
    return passwordResetToken;
  }

  @Override
  public void delete(PasswordResetToken resetToken) {
    passwordResetTokenRepository.delete(resetToken);

  }

}
