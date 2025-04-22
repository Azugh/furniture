package com.furniture.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.furniture.api.dto.CartItemDTO;
import com.furniture.core.model.CartItem;
import com.furniture.core.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {
  private final CartService cartService;

  @GetMapping
  public ResponseEntity<List<CartItemDTO>> getCartItems() {
    List<CartItem> items = cartService.getCartItems(getCurrentUserId());
    List<CartItemDTO> itemDTOs = items.stream()
        .map(this::convertToCartItemDTO)
        .toList();
    return ResponseEntity.ok(itemDTOs);
  }

  @PostMapping
  public ResponseEntity<CartItemDTO> addCartItem(@RequestBody CartItemDTO cartItemDTO) {
    CartItem item = cartService.addCartItem(
        getCurrentUserId(),
        cartItemDTO.getFurnitureId(),
        cartItemDTO.getQuantity());
    return ResponseEntity.ok(convertToCartItemDTO(item));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CartItemDTO> updateCartItemQuantity(
      @PathVariable Long id,
      @RequestParam int quantity) {
    cartService.updateCartItemQuantity(getCurrentUserId(), id, quantity);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
    cartService.removeCartItem(getCurrentUserId(), id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> clearCart() {
    cartService.clearCart(getCurrentUserId());
    return ResponseEntity.noContent().build();
  }

  private CartItemDTO convertToCartItemDTO(CartItem item) {
    CartItemDTO dto = new CartItemDTO();
    dto.setId(item.getId());
    dto.setFurnitureId(item.getFurniture().getId());
    dto.setQuantity(item.getQuantity());
    return dto;
  }

  private Long getCurrentUserId() {
    // TODO Implementation to get current user ID from security context
    return 1L; // Simplified for example
  }
}
