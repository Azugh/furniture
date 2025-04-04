package com.furniture.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
