package com.food.service.impl;

import com.food.dto.response.RestaurantResponseDto;
import com.food.dto.request.CreateRestaurantRequestDto;
import com.food.exception.restaurant.RestaurantNotFoundException;
import com.food.model.Address;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.repository.AddressRepository;
import com.food.repository.RestaurantRepository;
import com.food.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.food.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;
  private final MessageSource messageSource;

  @Override
  public Restaurant createRestaurant(CreateRestaurantRequestDto restaurantRequestDto, User user) {

    Address address = addressRepository.save(restaurantRequestDto.getAddress());

    Restaurant restaurant = new Restaurant();
    restaurant.setAddress(address);
    restaurant.setContactInformation(restaurantRequestDto.getContactInformation());
    restaurant.setCuisineType(restaurantRequestDto.getCuisineType());
    restaurant.setImages(restaurantRequestDto.getImages());
    restaurant.setDescription(restaurantRequestDto.getDescription());
    restaurant.setName(restaurantRequestDto.getName());
    restaurant.setOpeningHours(restaurantRequestDto.getOpeningHours());
    restaurant.setRegistrationDate(LocalDateTime.now());
    restaurant.setOwner(user);

    return restaurantRepository.save(restaurant);
  }

  @Override
  public Restaurant updateRestaurant(
      Long restaurantId, CreateRestaurantRequestDto updatedRestaurant) {

    Restaurant restaurant = findRestaurantById(restaurantId);

    if (restaurant.getCuisineType() != null) {
      restaurant.setCuisineType(updatedRestaurant.getCuisineType());
    }
    if (restaurant.getDescription() != null) {
      restaurant.setDescription(updatedRestaurant.getDescription());
    }
    if (restaurant.getName() != null) {
      restaurant.setName(updatedRestaurant.getName());
    }

    return restaurantRepository.save(restaurant);
  }

  @Override
  public void deleteRestaurant(Long restaurantId) {
    Restaurant restaurant = findRestaurantById(restaurantId);
    restaurantRepository.delete(restaurant);
  }

  @Override
  public List<Restaurant> getAllRestaurants() {

    return restaurantRepository.findAll();
  }

  @Override
  public List<Restaurant> searchRestaurant(String keyword) {
    return restaurantRepository.findBySearchQuery(keyword);
  }

  @Override
  public Restaurant findRestaurantById(Long id) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(id);
    if (restaurant.isEmpty()) {
      throw new RestaurantNotFoundException(messageSource);
    }
    return restaurant.get();
  }

  @Override
  public Restaurant getRestaurantByUserId(Long userId) {
    Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

    if (restaurant == null) {
      throw new RestaurantNotFoundException(messageSource, userId);
    }
    return restaurant;
  }

  @Override
  public RestaurantResponseDto addToFavorite(Long restaurantId, User user) {

    Restaurant restaurant = findRestaurantById(restaurantId);

    RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();
    restaurantResponseDto.setDescription(restaurant.getDescription());
    restaurantResponseDto.setImages(restaurant.getImages());
    restaurantResponseDto.setTitle(restaurant.getName());
    restaurantResponseDto.setId(restaurant.getId());

    boolean isFavorite = false;
    List<RestaurantResponseDto> favorites = user.getFavorites();
    for (RestaurantResponseDto favorite : favorites) {
      if (favorite.getId().equals(restaurantId)) {
        isFavorite = true;
        break;
      }
    }

    if (isFavorite) {
      favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
    } else {
      favorites.add(restaurantResponseDto);
    }

    userRepository.save(user);

    return restaurantResponseDto;
  }

  @Override
  public Restaurant updateRestaurantStatus(Long restaurantId) {

    Restaurant restaurant = findRestaurantById(restaurantId);
    restaurant.setOpen(!restaurant.isOpen());

    return restaurantRepository.save(restaurant);
  }
}
