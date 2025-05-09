package com.furniture.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furniture.core.model.Cart;
import com.furniture.core.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByUser(User user);
}
