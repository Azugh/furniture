package com.furniture.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.Cart;
import org.springframework.stereotype.Repository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByCustomerId(Long userId);
}
