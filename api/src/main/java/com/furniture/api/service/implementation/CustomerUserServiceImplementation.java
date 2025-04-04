package com.furniture.api.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.furniture.core.domain.UserRole;
import com.furniture.core.model.User;
import com.furniture.core.repository.UserRepository;

@Service
public class CustomerUserServiceImplementation implements UserDetailsService {

  private UserRepository userRepository;

  CustomerUserServiceImplementation(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(username);

    if (user == null) {

      throw new UsernameNotFoundException("user not found with email  - " + username);
    }

    UserRole role = user.getRole();

    if (role == null)
      role = UserRole.CUSTOMER;

    System.out.println("Роль  ---- " + role);

    List<GrantedAuthority> authorities = new ArrayList<>();

    authorities.add(new SimpleGrantedAuthority(role.toString()));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), authorities);
  }

}
