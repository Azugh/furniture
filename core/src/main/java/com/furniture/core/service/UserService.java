package com.furniture.core.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.furniture.core.model.User;
import com.furniture.core.model.UserAddress;

public interface UserService extends UserDetailsService {
  User registerUser(User user);

  User updateUser(User user);

  void deleteUser(Long userId);

  User getUserById(Long userId);

  List<UserAddress> getUserAddresses(Long userId);

  UserAddress addUserAddress(Long userId, UserAddress address);

  void deleteUserAddress(Long userId, Long addressId);

  List<User> getAllUsers();

  User changeUserRole(Long userId, User.Role newRole);

  boolean existsByEmail(String email);

  User getCurrentUser();
}
