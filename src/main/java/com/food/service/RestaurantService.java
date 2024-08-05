package com.food.service;

import com.food.dto.RestaurantDto;
import com.food.dto.request.CreateRestaurantRequestDto;
import com.food.model.Restaurant;
import com.food.model.User;
import java.util.List;

public interface RestaurantService {

  Restaurant createRestaurant(CreateRestaurantRequestDto restaurantRequestDto, User user);

  Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequestDto updatedRestaurant)
      throws Exception;

  void deleteRestaurant(Long restaurantId) throws Exception;

  List<Restaurant> getAllRestaurants();

  List<Restaurant> searchRestaurant(String keyword);

  Restaurant findRestaurantById(Long id) throws Exception;

  Restaurant getRestaurantByUserId(Long userId) throws Exception;

  RestaurantDto addToFavorite(Long restaurantId, User user) throws Exception;

  Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
