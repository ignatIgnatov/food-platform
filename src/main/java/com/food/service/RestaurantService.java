package com.food.service;

import com.food.dto.response.CreateRestaurantResponseDto;
import com.food.dto.request.CreateRestaurantRequestDto;
import com.food.dto.response.RestaurantResponseDto;
import com.food.model.Restaurant;
import com.food.model.User;
import java.util.List;

public interface RestaurantService {

  CreateRestaurantResponseDto createRestaurant(CreateRestaurantRequestDto restaurantRequestDto, User user);

  Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequestDto updatedRestaurant);

  void deleteRestaurant(Long restaurantId);

  List<RestaurantResponseDto> getAllRestaurants();

  List<Restaurant> searchRestaurant(String keyword);

  RestaurantResponseDto getRestaurantById(Long id);

  RestaurantResponseDto getRestaurantByUserId(Long userId);

  CreateRestaurantResponseDto addToFavorite(Long restaurantId, User user);

  Restaurant updateRestaurantStatus(Long restaurantId);

  Restaurant findRestaurantById(Long id);
}
