package com.food.controller;

import com.food.dto.request.AddCartItemRequestDto;
import com.food.dto.request.UpdateCartItemRequest;
import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PutMapping("/cart/add")
  public ResponseEntity<CartItem> addItemToCart(
      @RequestBody AddCartItemRequestDto request, @RequestHeader("Authorization") String jwt) {
    CartItem cartItem = cartService.addItemToCart(request, jwt);
    return new ResponseEntity<>(cartItem, HttpStatus.OK);
  }

  @PutMapping("/cart-item/update")
  public ResponseEntity<CartItem> updateCartItemQuantity(
      @RequestBody UpdateCartItemRequest request) {
    CartItem cartItem =
        cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
    return new ResponseEntity<>(cartItem, HttpStatus.OK);
  }

  @DeleteMapping("/cart-item/{id}/remove")
  public ResponseEntity<Cart> removeCartItem(
      @PathVariable("id") Long id, @RequestHeader("Authorization") String jwt) {
    Cart cart = cartService.removeItemFromCart(id, jwt);
    return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
  }

  @PutMapping("/cart/clear")
  public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) {
    Cart cart = cartService.clearCart(jwt);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @GetMapping("/cart")
  public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) {
    Cart cart = cartService.findCartByUserId(jwt);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }
}
