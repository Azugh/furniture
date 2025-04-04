package com.furniture.api.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furniture.api.service.CartService;
import com.furniture.api.service.NotificationService;
import com.furniture.api.service.OrderService;
import com.furniture.api.service.PaymentService;
import com.furniture.core.exception.CartException;
import com.furniture.core.exception.OrderException;
import com.furniture.core.exception.RestaurantException;
import com.furniture.core.exception.UserException;
import com.furniture.core.model.Address;
import com.furniture.core.model.Cart;
import com.furniture.core.model.CartItem;
import com.furniture.core.model.Notification;
import com.furniture.core.model.Order;
import com.furniture.core.model.OrderItem;
import com.furniture.core.model.PaymentResponse;
import com.furniture.core.model.Restaurant;
import com.furniture.core.model.User;
import com.furniture.core.repository.AddressRepository;
import com.furniture.core.repository.OrderItemRepository;
import com.furniture.core.repository.OrderRepository;
import com.furniture.core.repository.RestaurantRepository;
import com.furniture.core.repository.UserRepository;
import com.furniture.core.request.CreateOrderRequest;
import com.stripe.exception.StripeException;

@Service
public class OrderServiceImplementation implements OrderService {

  private AddressRepository addressRepository;
  private CartService cartService;
  private OrderItemRepository orderItemRepository;
  private OrderRepository orderRepository;
  private RestaurantRepository restaurantRepository;
  private UserRepository userRepository;
  private PaymentService paymentSerive;
  private NotificationService notificationService;

  public OrderServiceImplementation(AddressRepository addressRepository, CartService cartService,
      OrderItemRepository orderItemRepository, OrderRepository orderRepository,
      RestaurantRepository restaurantRepository, UserRepository userRepository, PaymentService paymentSerive,
      NotificationService notificationService) {
    this.addressRepository = addressRepository;
    this.cartService = cartService;
    this.orderItemRepository = orderItemRepository;
    this.orderRepository = orderRepository;
    this.restaurantRepository = restaurantRepository;
    this.userRepository = userRepository;
    this.paymentSerive = paymentSerive;
    this.notificationService = notificationService;
  }

  @Override
  public PaymentResponse createOrder(CreateOrderRequest order, User user)
      throws UserException, RestaurantException, CartException, StripeException {

    Address shippAddress = order.getDeliveryAddress();

    Address savedAddress = addressRepository.save(shippAddress);

    if (!user.getAddresses().contains(savedAddress)) {
      user.getAddresses().add(savedAddress);
    }

    System.out.println("user addresses --------------  " + user.getAddresses());

    userRepository.save(user);

    Optional<Restaurant> restaurant = restaurantRepository.findById(order.getRestaurantId());
    if (restaurant.isEmpty()) {
      throw new RestaurantException("Restaurant not found with id " + order.getRestaurantId());
    }

    Order createdOrder = new Order();

    createdOrder.setCustomer(user);
    createdOrder.setDeliveryAddress(savedAddress);
    createdOrder.setCreatedAt(new Date());
    createdOrder.setOrderStatus("PENDING");
    createdOrder.setRestaurant(restaurant.get());

    Cart cart = cartService.findCartByUserId(user.getId());

    List<OrderItem> orderItems = new ArrayList<>();

    for (CartItem cartItem : cart.getItems()) {
      OrderItem orderItem = new OrderItem();
      orderItem.setFood(cartItem.getFood());
      orderItem.setIngredients(cartItem.getIngredients());
      orderItem.setQuantity(cartItem.getQuantity());
      orderItem.setTotalPrice(cartItem.getFood().getPrice() * cartItem.getQuantity());
      OrderItem savedOrderItem = orderItemRepository.save(orderItem);
      orderItems.add(savedOrderItem);
    }

    Long totalPrice = cartService.calculateCartTotals(cart);

    createdOrder.setTotalAmount(totalPrice);
    createdOrder.setRestaurant(restaurant.get());

    createdOrder.setItems(orderItems);
    Order savedOrder = orderRepository.save(createdOrder);

    restaurant.get().getOrders().add(savedOrder);

    restaurantRepository.save(restaurant.get());

    PaymentResponse res = paymentSerive.generatePaymentLink(savedOrder);
    return res;

  }

  @Override
  public void cancelOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    if (order == null) {
      throw new OrderException("Order not found with the id " + orderId);
    }

    orderRepository.deleteById(orderId);

  }

  public Order findOrderById(Long orderId) throws OrderException {
    Optional<Order> order = orderRepository.findById(orderId);
    if (order.isPresent())
      return order.get();

    throw new OrderException("Order not found with the id " + orderId);
  }

  @Override
  public List<Order> getUserOrders(Long userId) throws OrderException {
    List<Order> orders = orderRepository.findAllUserOrders(userId);
    return orders;
  }

  @Override
  public List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus)
      throws OrderException, RestaurantException {

    List<Order> orders = orderRepository.findOrdersByRestaurantId(restaurantId);

    if (orderStatus != null) {
      orders = orders.stream()
          .filter(order -> order.getOrderStatus().equals(orderStatus))
          .collect(Collectors.toList());
    }

    return orders;
  }
  // private List<MenuItem> filterByVegetarian(List<MenuItem> menuItems, boolean
  // isVegetarian) {
  // return menuItems.stream()
  // .filter(menuItem -> menuItem.isVegetarian() == isVegetarian)
  // .collect(Collectors.toList());
  // }

  @Override
  public Order updateOrder(Long orderId, String orderStatus) throws OrderException {
    Order order = findOrderById(orderId);

    System.out.println("--------- " + orderStatus);

    if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED")
        || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
      order.setOrderStatus(orderStatus);
      Notification notification = notificationService.sendOrderStatusNotification(order);
      return orderRepository.save(order);
    } else
      throw new OrderException("Please Select A Valid Order Status");

  }

}
