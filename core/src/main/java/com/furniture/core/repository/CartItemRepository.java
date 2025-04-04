package com.furniture.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
