package com.furniture.core.request;

import com.furniture.core.model.Address;

import lombok.Data;

@Data
public class CreateOrderRequest {

  private Long restaurantId;
  private Address deliveryAddress;
}
