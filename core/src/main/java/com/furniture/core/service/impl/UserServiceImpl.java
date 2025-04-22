package com.furniture.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.furniture.core.model.Cart;
import com.furniture.core.model.User;
import com.furniture.core.model.UserAddress;
import com.furniture.core.repository.CartRepository;
import com.furniture.core.repository.UserAddressRepository;
import com.furniture.core.repository.UserRepository;
import com.furniture.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserAddressRepository userAddressRepository;
  private final CartRepository cartRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User registerUser(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new RuntimeException("Email already in use");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(User.Role.CUSTOMER);
    User savedUser = userRepository.save(user);

    // Create a cart for the new user
    Cart cart = new Cart();
    cart.setUser(savedUser);
    cartRepository.save(cart);

    return savedUser;
  }

  @Override
  public User updateUser(User user) {
    User existingUser = userRepository.findById(user.getId())
        .orElseThrow(() -> new RuntimeException("User not found"));
    existingUser.setFirstName(user.getFirstName());
    existingUser.setLastName(user.getLastName());
    existingUser.setPhone(user.getPhone());
    return userRepository.save(existingUser);
  }

  @Override
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public List<UserAddress> getUserAddresses(Long userId) {
    User user = getUserById(userId);
    return user.getAddresses();
  }

  @Override
  public UserAddress addUserAddress(Long userId, UserAddress address) {
    User user = getUserById(userId);
    address.setUser(user);
    return userAddressRepository.save(address);
  }

  @Override
  public void deleteUserAddress(Long userId, Long addressId) {
    User user = getUserById(userId);
    user.getAddresses().removeIf(address -> address.getId().equals(addressId));
    userAddressRepository.deleteById(addressId);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User changeUserRole(Long userId, User.Role newRole) {
    User user = getUserById(userId);
    user.setRole(newRole);
    return userRepository.save(user);
  }

  @Override
  public User loadUserByUsername(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return null; // Пользователь не аутентифицирован
    }
    String currentUserEmail = authentication.getName();
    Optional<User> userOptional = userRepository.findByEmail(currentUserEmail);
    return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public boolean existsByEmail(String email) {
    // TODO Auto-generated method stub
    return userRepository.existsByEmail(email);
  }
}
