package com.furniture.api.service.implementation;

import java.util.Optional;

import com.furniture.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

import com.furniture.api.service.CartService;
import com.furniture.core.exception.CartException;
import com.furniture.core.exception.CartItemException;
import com.furniture.core.exception.FoodException;
import com.furniture.core.exception.UserException;
import com.furniture.core.model.Cart;
import com.furniture.core.model.CartItem;
import com.furniture.core.model.Food;
import com.furniture.core.model.User;
import com.furniture.core.repository.CartItemRepository;
import com.furniture.core.repository.CartRepository;
import com.furniture.core.repository.FoodRepository;
import com.furniture.core.request.AddCartItemRequest;

@Service
public class CartServiceImplementation implements CartService {

  private CartRepository cartRepository;
  private UserService userService;
  private CartItemRepository cartItemRepository;
  private FoodRepository menuItemRepository;

  @Autowired
  CartServiceImplementation(
      CartRepository cartRepository,
      UserService userService,
      CartItemRepository cartItemRepository,
      FoodRepository menuItemRepository) {

    this.cartItemRepository = cartItemRepository;
    this.cartRepository = cartRepository;
    this.userService = userService;
    this.menuItemRepository = menuItemRepository;
  }

  // @Override
  // public CartItemResponse addItemToCart(AddCartItemRequest req, String jwt)
  // throws UserException, FoodException, CartException, CartItemException {
  //
  // // Найти пользователя по JWT
  // User user = userService.findUserProfileByJwt(jwt);
  //
  // // Найти продукт (Food) по ID
  // Optional<Food> menuItem = menuItemRepository.findById(req.getMenuItemId());
  // if (menuItem.isEmpty()) {
  // throw new FoodException("Menu Item not exist with id " +
  // req.getMenuItemId());
  // }
  //
  // // Найти корзину пользователя
  // Cart cart = findCartByUserId(user.getId());
  //
  // // Проверить, существует ли уже такой товар в корзине
  // for (CartItem cartItem : cart.getItems()) {
  // if (cartItem.getFood().equals(menuItem.get())) {
  // // Если товар уже есть в корзине, обновить количество
  // int newQuantity = cartItem.getQuantity() + req.getQuantity();
  // CartItem updatedItem = updateCartItemQuantity(cartItem.getId(), newQuantity);
  // return toCartItemResponse(updatedItem); // Вернуть CartItemResponse
  // }
  // }
  //
  // // Создать новый товар в корзине
  // CartItem newCartItem = new CartItem();
  // newCartItem.setFood(menuItem.get());
  // newCartItem.setQuantity(req.getQuantity());
  // newCartItem.setCart(cart);
  // newCartItem.setIngredients(req.getIngredients());
  // newCartItem.setTotalPrice(req.getQuantity() * menuItem.get().getPrice());
  //
  // // Сохранить новый товар в корзине
  // CartItem savedItem = cartItemRepository.save(newCartItem);
  // cart.getItems().add(savedItem);
  // cartRepository.save(cart);
  //
  // // Вернуть CartItemResponse
  // return toCartItemResponse(savedItem);
  // }
  //
  // private CartItemResponse toCartItemResponse(CartItem cartItem) {
  // return new CartItemResponse(
  // cartItem.getId(), // ID товара в корзине
  // cartItem.getFood().getId(), // ID продукта
  // cartItem.getFood().getName(), // Название продукта
  // cartItem.getQuantity(), // Количество
  // new BigDecimal(cartItem.getFood().getPrice()), // Цена за единицу
  // new BigDecimal(cartItem.getTotalPrice()) // Общая стоимость
  // );
  // }
  //
  @Override
  public CartItem addItemToCart(AddCartItemRequest req, String jwt)
      throws UserException, FoodException, CartException, CartItemException {

    User user = userService.findUserProfileByJwt(jwt);

    Optional<Food> menuItem = menuItemRepository.findById(req.getMenuItemId());
    if (menuItem.isEmpty()) {
      throw new FoodException("Menu Item not exist with id " +
          req.getMenuItemId());
    }

    Cart cart = findCartByUserId(user.getId());

    for (CartItem cartItem : cart.getItems()) {
      if (cartItem.getFood().equals(menuItem.get())) {

        int newQuantity = cartItem.getQuantity() + req.getQuantity();
        return updateCartItemQuantity(cartItem.getId(), newQuantity);
      }
    }

    CartItem newCartItem = new CartItem();
    newCartItem.setFood(menuItem.get());
    newCartItem.setQuantity(req.getQuantity());
    newCartItem.setCart(cart);
    newCartItem.setIngredients(req.getIngredients());
    newCartItem.setTotalPrice(req.getQuantity() * menuItem.get().getPrice());

    CartItem savedItem = cartItemRepository.save(newCartItem);
    cart.getItems().add(savedItem);
    cartRepository.save(cart);

    return savedItem;

  }

  @Override
  public CartItem updateCartItemQuantity(Long cartItemId, int newQuantity) throws CartItemException {
    Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
    if (cartItem.isEmpty()) {
      throw new CartItemException("cart item not exist with id " + cartItemId);
    }
    cartItem.get().setQuantity(newQuantity);
    cartItem.get().setTotalPrice((cartItem.get().getFood().getPrice() * newQuantity));
    return cartItemRepository.save(cartItem.get());
  }

  @Override
  public Cart removeItemFromCart(Long cartItemId, String jwt) throws UserException,
      CartException, CartItemException {

    User user = userService.findUserProfileByJwt(jwt);

    Cart cart = findCartByUserId(user.getId());

    Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

    if (cartItem.isEmpty()) {
      throw new CartItemException("cart item not exist with id " + cartItemId);
    }

    cart.getItems().remove(cartItem.get());
    return cartRepository.save(cart);
  }

  @Override
  public Long calculateCartTotals(Cart cart) throws UserException {

    Long total = 0L;
    for (CartItem cartItem : cart.getItems()) {
      total += cartItem.getFood().getPrice() * cartItem.getQuantity();
    }
    return total;
  }

  @Override
  public Cart findCartById(Long id) throws CartException {
    Optional<Cart> cart = cartRepository.findById(id);
    if (cart.isPresent()) {
      return cart.get();
    }
    throw new CartException("Cart not found with the id " + id);
  }

  @Override
  public Cart findCartByUserId(Long userId) throws CartException, UserException {

    Optional<Cart> opt = cartRepository.findByCustomer_Id(userId);

    if (opt.isPresent()) {
      return opt.get();
    }
    throw new CartException("cart not found");

  }

  @Override
  public Cart clearCart(Long userId) throws CartException, UserException {
    Cart cart = findCartByUserId(userId);

    cart.getItems().clear();
    return cartRepository.save(cart);
  }

}
