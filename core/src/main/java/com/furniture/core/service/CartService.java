package com.furniture.core.service;

import java.util.List;

import com.furniture.core.model.CartItem;

public interface CartService {
  List<CartItem> getCartItems(Long userId);

  CartItem addCartItem(Long userId, Long furnitureId, int quantity);

  void updateCartItemQuantity(Long userId, Long cartItemId, int quantity);

  void removeCartItem(Long userId, Long cartItemId);

  void clearCart(Long userId);
}
