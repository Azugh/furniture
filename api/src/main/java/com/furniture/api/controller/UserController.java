package com.furniture.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.api.dto.AddressDTO;
import com.furniture.api.dto.UserDTO;
import com.furniture.core.model.User;
import com.furniture.core.model.UserAddress;
import com.furniture.core.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/me")
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OWNER')")
  public ResponseEntity<UserDTO> getCurrentUser() {
    User user = userService.getCurrentUser();
    return ResponseEntity.ok(UserDTO.fromEntity(user));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    List<UserDTO> userDTOs = users.stream().map(UserDTO::fromEntity).toList();
    return ResponseEntity.ok(userDTOs);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    return ResponseEntity.ok(UserDTO.fromEntity(user));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
    userDTO.setId(id);
    User updatedUser = userService.updateUser(userDTO.toEntity());
    return ResponseEntity.ok(UserDTO.fromEntity(updatedUser));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{userId}/addresses")
  @PreAuthorize("hasRole('ADMIN') or #userId == principal.id")
  public ResponseEntity<List<AddressDTO>> getUserAddresses(@PathVariable Long userId) {
    List<UserAddress> addresses = userService.getUserAddresses(userId);
    List<AddressDTO> addressDTOs = addresses.stream().map(this::convertToAddressDTO).toList();
    return ResponseEntity.ok(addressDTOs);
  }

  @PostMapping("/{userId}/addresses")
  @PreAuthorize("hasRole('ADMIN') or #userId == principal.id")
  public ResponseEntity<AddressDTO> addUserAddress(
      @PathVariable Long userId,
      @Valid @RequestBody AddressDTO addressDTO) {
    UserAddress address = convertToAddressEntity(addressDTO);
    UserAddress savedAddress = userService.addUserAddress(userId, address);
    return ResponseEntity.ok(convertToAddressDTO(savedAddress));
  }

  @DeleteMapping("/{userId}/addresses/{addressId}")
  @PreAuthorize("hasRole('ADMIN') or #userId == principal.id")
  public ResponseEntity<Void> deleteUserAddress(
      @PathVariable Long userId,
      @PathVariable Long addressId) {
    userService.deleteUserAddress(userId, addressId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/role")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDTO> changeUserRole(
      @PathVariable Long id,
      @RequestParam User.Role newRole) {
    User updatedUser = userService.changeUserRole(id, newRole);
    return ResponseEntity.ok(UserDTO.fromEntity(updatedUser));
  }

  private AddressDTO convertToAddressDTO(UserAddress address) {
    AddressDTO dto = new AddressDTO();
    dto.setId(address.getId());
    dto.setCountry(address.getCountry());
    dto.setCity(address.getCity());
    dto.setStreet(address.getStreet());
    dto.setBuilding(address.getBuilding());
    dto.setApartment(address.getApartment());
    dto.setPostalCode(address.getPostalCode());
    return dto;
  }

  private UserAddress convertToAddressEntity(AddressDTO dto) {
    UserAddress address = new UserAddress();
    address.setId(dto.getId());
    address.setCountry(dto.getCountry());
    address.setCity(dto.getCity());
    address.setStreet(dto.getStreet());
    address.setBuilding(dto.getBuilding());
    address.setApartment(dto.getApartment());
    address.setPostalCode(dto.getPostalCode());
    return address;
  }
}
