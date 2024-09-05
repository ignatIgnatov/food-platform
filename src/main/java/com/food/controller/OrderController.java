package com.food.controller;

import com.food.dto.request.OrderRequestDto;
import com.food.dto.response.OrderResponseDto;
import com.food.dto.response.PaymentResponseDto;
import com.food.dto.response.UserResponseDto;
import com.food.model.Order;
import com.food.model.User;
import com.food.service.OrderService;
import com.food.service.PaymentService;
import com.food.service.UserService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createOrder(@RequestBody OrderRequestDto request, @RequestHeader("Authorization") String jwt) throws StripeException {
        OrderResponseDto orderResponseDto = orderService.createOrder(request, jwt);
        Order order = orderService.findOrderById(orderResponseDto.getId());
        PaymentResponseDto response = paymentService.createPaymentLink(order);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(@RequestHeader("Authorization") String jwt) {
        UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
        User user = userService.findUserByEmail(userResponseDto.getEmail());
        List<OrderResponseDto> orders = orderService.getUserOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
