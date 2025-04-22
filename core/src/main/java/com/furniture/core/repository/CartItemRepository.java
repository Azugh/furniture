package com.furniture.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furniture.core.model.Cart;
import com.furniture.core.model.CartItem;
import com.furniture.core.model.Furniture;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  Optional<CartItem> findByCartAndFurniture(Cart cart, Furniture furniture);

  void deleteByCart(Cart cart);
}
