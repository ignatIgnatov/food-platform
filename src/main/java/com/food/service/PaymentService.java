package com.food.service;

import com.food.dto.response.PaymentResponseDto;
import com.food.model.Order;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentResponseDto createPaymentLink(Order order) throws StripeException;

}
