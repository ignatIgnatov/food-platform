package com.food.service.impl;

import com.food.dto.request.AddCartItemRequestDto;
import com.food.dto.response.CartItemResponseDto;
import com.food.dto.response.CartResponseDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final FoodService foodService;
    private final MessageSource messageSource;
    private final ModelMapper modelMapper;

    @Override
    public CartItemResponseDto addItemToCart(AddCartItemRequestDto requestDto, String jwt) {
        User user = getUserFromToken(jwt);
        Food food = foodService.findFoodById(requestDto.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + requestDto.getQuantity();
                return modelMapper.map(updateCartItemQuantity(cartItem.getId(), newQuantity), CartItemResponseDto.class);
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

        return modelMapper.map(savedCartItem, CartItemResponseDto.class);
    }

    @Override
    public CartItemResponseDto updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = findCartItemById(cartItemId);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
        cartItem.getCart().setTotal(calculateCartTotals(cartItem.getCart()));
        cartItemRepository.save(cartItem);
        return modelMapper.map(cartItem, CartItemResponseDto.class);
    }

    @Override
    public CartResponseDto removeItemFromCart(Long cartItemId, String jwt) {
        User user = getUserFromToken(jwt);
        Cart cart = findCartById(user.getId());
        CartItem cartItem = findCartItemById(cartItemId);

        cart.setTotal(cart.getTotal() - cartItem.getTotalPrice());
        cart.getItems().remove(cartItem);

        cartRepository.save(cart);
        return modelMapper.map(cart, CartResponseDto.class);
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
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(messageSource));
    }

    @Override
    public CartResponseDto findCartByUserId(String jwt) {
        User user = getUserFromToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        return modelMapper.map(cart, CartResponseDto.class);
    }

    @Override
    public CartResponseDto clearCart(String jwt) {
        CartResponseDto cartResponseDto = findCartByUserId(jwt);
        Cart cart = findCartById(cartResponseDto.getId());
        cart.getItems().clear();
        cart.setTotal(null);
        cartRepository.save(cart);
        return modelMapper.map(cart, CartResponseDto.class);
    }

    private CartItem findCartItemById(Long id) {
        return cartItemRepository
                .findById(id)
                .orElseThrow(() -> new CartItemNotFoundException(messageSource));
    }

    private User getUserFromToken(String jwt) {
        UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
        User user = userService.findUserByEmail(userResponseDto.getEmail());
        return user;
    }
}
