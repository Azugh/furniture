package com.furniture.api.service;

import java.util.List;

import com.furniture.core.exception.CartException;
import com.furniture.core.exception.OrderException;
import com.furniture.core.exception.RestaurantException;
import com.furniture.core.exception.UserException;
import com.furniture.core.model.Order;
import com.furniture.core.model.PaymentResponse;
import com.furniture.core.model.User;
import com.furniture.core.request.CreateOrderRequest;
import com.stripe.exception.StripeException;

public interface OrderService {

  public PaymentResponse createOrder(CreateOrderRequest order, User user)
      throws UserException, RestaurantException, CartException, StripeException;

  public Order updateOrder(Long orderId, String orderStatus) throws OrderException;

  public void cancelOrder(Long orderId) throws OrderException;

  public List<Order> getUserOrders(Long userId) throws OrderException;

  public List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus)
      throws OrderException, RestaurantException;

}
