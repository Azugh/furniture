package com.furniture.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.Date;


//TODO Возможно @RequiredArgsConstructor
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PasswordResetToken {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private @NonNull String token;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  private @NonNull User user;

  private @NonNull  Date expireDate;

  public boolean isExpired() {
    return expireDate.before(new Date());
  }

//  PasswordResetToken(String token, User user, Date expireDay) {
//    this.token = token;
//    this.user = user;
//    this.expireDate = expireDay;
//  }
}
