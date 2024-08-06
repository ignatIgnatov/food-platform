package com.food.service;

import com.food.dto.response.RestaurantResponseDto;
import com.food.dto.request.CreateRestaurantRequestDto;
import com.food.model.Restaurant;
import com.food.model.User;
import java.util.List;

public interface RestaurantService {

  Restaurant createRestaurant(CreateRestaurantRequestDto restaurantRequestDto, User user);

  Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequestDto updatedRestaurant);

  void deleteRestaurant(Long restaurantId);

  List<Restaurant> getAllRestaurants();

  List<Restaurant> searchRestaurant(String keyword);

  Restaurant findRestaurantById(Long id);

  Restaurant getRestaurantByUserId(Long userId);

  RestaurantResponseDto addToFavorite(Long restaurantId, User user);

  Restaurant updateRestaurantStatus(Long restaurantId);
}
