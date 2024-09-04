package com.food.service;

import com.food.dto.request.AddCartItemRequestDto;
import com.food.dto.response.CartItemResponseDto;
import com.food.dto.response.CartResponseDto;
import com.food.model.Cart;
import com.food.model.CartItem;

public interface CartService {

  CartItemResponseDto addItemToCart(AddCartItemRequestDto requestDto, String jwt);

  CartItemResponseDto updateCartItemQuantity(Long cartItemId, int quantity);

  CartResponseDto removeItemFromCart(Long cartItemId, String jwt);

  Long calculateCartTotals(Cart cart);

  Cart findCartById(Long id);

  CartResponseDto findCartByUserId(String jwt);

  CartResponseDto clearCart(String jwt);
}
