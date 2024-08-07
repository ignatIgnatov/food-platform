package com.food.service;

import com.food.dto.request.AddCartItemRequestDto;
import com.food.model.Cart;
import com.food.model.CartItem;

public interface CartService {

  CartItem addItemToCart(AddCartItemRequestDto requestDto, String jwt);

  CartItem updateCartItemQuantity(Long cartItemId, int quantity);

  Cart removeItemFromCart(Long cartItemId, String jwt);

  Long calculateCartTotals(Cart cart);

  Cart findCartById(Long id);

  Cart findCartByUserId(String jwt);

  Cart clearCart(String jwt);
}
