package com.furniture.api.service;

import com.furniture.core.model.Order;
import com.furniture.core.model.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

  public PaymentResponse generatePaymentLink(Order order) throws StripeException;

}
