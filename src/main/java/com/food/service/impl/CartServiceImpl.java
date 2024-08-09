package com.food.service.impl;

import com.food.dto.request.AddCartItemRequestDto;
import com.food.dto.response.UserResponseDto;
import com.food.exception.cart.CartItemNotFoundException;
import com.food.exception.cart.CartNotFoundException;
import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.model.Food;
import com.food.model.User;
import com.food.repository.CartItemRepository;
import com.food.repository.CartRepository;
import com.food.service.CartService;
import com.food.service.FoodService;
import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final UserService userService;
  private final FoodService foodService;

  @Override
  public CartItem addItemToCart(AddCartItemRequestDto requestDto, String jwt) {
    User user = getUserFromToken(jwt);
    Food food = foodService.findFoodById(requestDto.getFoodId());

    Cart cart = cartRepository.findByCustomerId(user.getId());

    for (CartItem cartItem : cart.getItems()) {
      if (cartItem.getFood().equals(food)) {
        int newQuantity = cartItem.getQuantity() + requestDto.getQuantity();
        return updateCartItemQuantity(cartItem.getId(), newQuantity);
      }
    }

    CartItem newCartItem = new CartItem();
    newCartItem.setFood(food);
    newCartItem.setCart(cart);
    newCartItem.setQuantity(requestDto.getQuantity());
    newCartItem.setIngredients(requestDto.getIngredients());
    newCartItem.setTotalPrice(requestDto.getQuantity() * food.getPrice());

    CartItem savedCartItem = cartItemRepository.save(newCartItem);

    cart.getItems().add(savedCartItem);
    cart.setTotal(calculateCartTotals(cart));
    cartRepository.save(cart);

    return savedCartItem;
  }

  @Override
  public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
    CartItem cartItem = findCartItemById(cartItemId);
    cartItem.setQuantity(quantity);
    cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
    cartItem.getCart().setTotal(calculateCartTotals(cartItem.getCart()));
    return cartItemRepository.save(cartItem);
  }

  @Override
  public Cart removeItemFromCart(Long cartItemId, String jwt) {
    Cart cart = findCartByUserId(jwt);
    CartItem cartItem = findCartItemById(cartItemId);

    cart.getItems().remove(cartItem);
    cart.setTotal(cart.getTotal() - cartItem.getTotalPrice());
    return cartRepository.save(cart);
  }

  @Override
  public Long calculateCartTotals(Cart cart) {
    Long total = 0L;

    for (CartItem cartItem : cart.getItems()) {
      total += cartItem.getFood().getPrice() * cartItem.getQuantity();
    }
    return total;
  }

  @Override
  public Cart findCartById(Long id) {
    return cartRepository.findById(id).orElseThrow(CartNotFoundException::new);
  }

  @Override
  public Cart findCartByUserId(String jwt) {
    User user = getUserFromToken(jwt);
    return cartRepository.findByCustomerId(user.getId());
  }

  @Override
  public Cart clearCart(String jwt) {
    Cart cart = findCartByUserId(jwt);
    cart.getItems().clear();
    cart.setTotal(null);
    return cartRepository.save(cart);
  }

  private CartItem findCartItemById(Long id) {
    return cartItemRepository.findById(id).orElseThrow(CartItemNotFoundException::new);
  }

  private User getUserFromToken(String jwt) {
    UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
    User user = userService.findUserByEmail(userResponseDto.getEmail());
    return user;
  }
}
