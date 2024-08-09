package com.food.controller;

import com.food.model.Order;
import com.food.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {

  private final OrderService orderService;

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<List<Order>> getRestaurantOrders(
      @PathVariable("id") Long id, @RequestParam(required = false) String orderStatus) {
    List<Order> orders = orderService.getRestaurantOrders(id, orderStatus);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PutMapping("/{id}/{status}")
  public ResponseEntity<Order> updateOrderStatus(
      @PathVariable("id") Long id, @PathVariable("status") String status) {
    Order order = orderService.updateOrder(id, status);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
