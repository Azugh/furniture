package com.furniture.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.furniture.core.model.Cart;
import com.furniture.core.model.CartItem;
import com.furniture.core.model.Furniture;
import com.furniture.core.model.User;
import com.furniture.core.repository.CartItemRepository;
import com.furniture.core.repository.CartRepository;
import com.furniture.core.repository.FurnitureRepository;
import com.furniture.core.repository.UserRepository;
import com.furniture.core.service.CartService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final FurnitureRepository furnitureRepository;
  private final UserRepository userRepository;

  @Override
  public List<CartItem> getCartItems(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> new RuntimeException("Cart not found"));
    return cart.getItems();
  }

  @Override
  public CartItem addCartItem(Long userId, Long furnitureId, int quantity) {
    if (quantity <= 0) {
      throw new RuntimeException("Quantity must be positive");
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> new RuntimeException("Cart not found"));
    Furniture furniture = furnitureRepository.findById(furnitureId)
        .orElseThrow(() -> new RuntimeException("Furniture not found"));

    // Check if item already exists in cart
    Optional<CartItem> existingItem = cart.getItems().stream()
        .filter(item -> item.getFurniture().getId().equals(furnitureId))
        .findFirst();

    if (existingItem.isPresent()) {
      CartItem item = existingItem.get();
      item.setQuantity(item.getQuantity() + quantity);
      return cartItemRepository.save(item);
    } else {
      CartItem newItem = new CartItem();
      newItem.setCart(cart);
      newItem.setFurniture(furniture);
      newItem.setQuantity(quantity);
      return cartItemRepository.save(newItem);
    }
  }

  @Override
  public void updateCartItemQuantity(Long userId, Long cartItemId, int quantity) {
    if (quantity <= 0) {
      throw new RuntimeException("Quantity must be positive");
    }

    CartItem item = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new RuntimeException("Cart item not found"));

    // Verify the item belongs to the user's cart
    if (!item.getCart().getUser().getId().equals(userId)) {
      throw new RuntimeException("Cart item does not belong to user");
    }

    item.setQuantity(quantity);
    cartItemRepository.save(item);
  }

  @Override
  public void removeCartItem(Long userId, Long cartItemId) {
    CartItem item = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new RuntimeException("Cart item not found"));

    // Verify the item belongs to the user's cart
    if (!item.getCart().getUser().getId().equals(userId)) {
      throw new RuntimeException("Cart item does not belong to user");
    }

    cartItemRepository.delete(item);
  }

  @Override
  public void clearCart(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> new RuntimeException("Cart not found"));
    cartItemRepository.deleteAllByCart(cart);
  }
}
