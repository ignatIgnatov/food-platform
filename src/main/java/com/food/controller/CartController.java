package com.food.controller;

import com.food.dto.request.AddCartItemRequestDto;
import com.food.dto.request.UpdateCartItemRequest;
import com.food.dto.response.CartItemResponseDto;
import com.food.dto.response.CartResponseDto;
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
    public ResponseEntity<CartItemResponseDto> addItemToCart(
            @RequestBody AddCartItemRequestDto request, @RequestHeader("Authorization") String jwt) {
        CartItemResponseDto cartItem = cartService.addItemToCart(request, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItemResponseDto> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest request, @RequestHeader("Authorization") String jwt) {
        CartItemResponseDto cartItem =
                cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<CartResponseDto> removeCartItem(
            @PathVariable("id") Long id, @RequestHeader("Authorization") String jwt) {
        CartResponseDto cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<CartResponseDto> clearCart(@RequestHeader("Authorization") String jwt) {
        CartResponseDto cart = cartService.clearCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponseDto> findUserCart(@RequestHeader("Authorization") String jwt) {
        CartResponseDto cart = cartService.findCartByUserId(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
