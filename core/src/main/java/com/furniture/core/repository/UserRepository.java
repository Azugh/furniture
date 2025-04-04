package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.furniture.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u Where u.status='PENDING'")
  List<User> getPenddingRestaurantOwners();

  User findByEmail(String username);
}
