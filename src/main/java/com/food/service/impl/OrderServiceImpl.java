package com.food.service.impl;

import com.food.dto.request.OrderRequestDto;
import com.food.dto.response.CartResponseDto;
import com.food.dto.response.OrderResponseDto;
import com.food.dto.response.UserResponseDto;
import com.food.exception.order.OrderNotFoundException;
import com.food.exception.order.OrderStatusException;
import com.food.model.*;
import com.food.repository.AddressRepository;
import com.food.repository.OrderItemRepository;
import com.food.repository.OrderRepository;
import com.food.repository.UserRepository;
import com.food.service.CartService;
import com.food.service.OrderService;
import com.food.service.RestaurantService;
import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MessageSource messageSource;
    private final UserService userService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RestaurantService restaurantService;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, String jwt) {
        Address shippingAddress = orderRequestDto.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);

        UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
        User user = userService.findUserByEmail(userResponseDto.getEmail());

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(shippingAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(orderRequestDto.getRestaurantId());

        Order order = new Order();
        order.setCustomer(user);
        order.setDeliveryAddress(savedAddress);
        order.setCreatedDate(new Date());
        order.setOrderStatus("PENDING");
        order.setRestaurant(restaurant);

        CartResponseDto cartResponseDto = cartService.findCartByUserId(jwt);
        Cart cart = cartService.findCartById(cartResponseDto.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        order.setItems(orderItems);
        order.setTotalPrice(cartService.calculateCartTotals(cart));

        Order savedOrder = orderRepository.save(order);

        restaurant.getOrders().add(savedOrder);

        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, String orderStatus) {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            return modelMapper.map(order, OrderResponseDto.class);
        }

        throw new OrderStatusException(messageSource);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        return orderRepository.findByCustomerId(userId).stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .toList();
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            return orders.stream()
                    .filter(order -> order.getOrderStatus().equals(orderStatus))
                    .toList();
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(messageSource));
    }
}
