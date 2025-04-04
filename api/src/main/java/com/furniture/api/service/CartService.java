package com.furniture.api.service;

import com.furniture.api.response.CartItemResponse;
import com.furniture.core.exception.CartException;
import com.furniture.core.exception.CartItemException;
import com.furniture.core.exception.FoodException;
import com.furniture.core.exception.UserException;
import com.furniture.core.model.Cart;
import com.furniture.core.model.CartItem;
import com.furniture.core.request.AddCartItemRequest;

public interface CartService {

  public CartItem addItemToCart(AddCartItemRequest req, String jwt)
      throws UserException, FoodException, CartException, CartItemException;

  public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws CartItemException;

  public Cart removeItemFromCart(Long cartItemId, String jwt) throws UserException, CartException, CartItemException;

  public Long calculateCartTotals(Cart cart) throws UserException;

  public Cart findCartById(Long id) throws CartException;

  public Cart findCartByUserId(Long userId) throws CartException, UserException;

  public Cart clearCart(Long userId) throws CartException, UserException;
}
