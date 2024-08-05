package com.food.service;

import com.food.dto.RestaurantDto;
import com.food.dto.request.CreateRestaurantRequestDto;
import com.food.model.Address;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.repository.AddressRepository;
import com.food.repository.RestaurantRepository;
import com.food.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;

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
      Long restaurantId, CreateRestaurantRequestDto updatedRestaurant) throws Exception {

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
  public void deleteRestaurant(Long restaurantId) throws Exception {
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
  public Restaurant findRestaurantById(Long id) throws Exception {
    Optional<Restaurant> restaurant = restaurantRepository.findById(id);
    if (restaurant.isEmpty()) {
      throw new Exception("Restaurant with id: " + id + " not found!");
    }
    return restaurant.get();
  }

  @Override
  public Restaurant getRestaurantByUserId(Long userId) throws Exception {
    Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

    if (restaurant == null) {
      throw new Exception("Restaurant with owner id: " + userId + " not found!");
    }
    return restaurant;
  }

  @Override
  public RestaurantDto addToFavorite(Long restaurantId, User user) throws Exception {

    Restaurant restaurant = findRestaurantById(restaurantId);

    RestaurantDto restaurantDto = new RestaurantDto();
    restaurantDto.setDescription(restaurant.getDescription());
    restaurantDto.setImages(restaurant.getImages());
    restaurantDto.setTitle(restaurant.getName());
    restaurantDto.setId(restaurant.getId());

    boolean isFavorite = false;
    List<RestaurantDto> favorites = user.getFavorites();
    for (RestaurantDto favorite : favorites) {
      if (favorite.getId().equals(restaurantId)) {
        isFavorite = true;
        break;
      }
    }

    if (isFavorite) {
      favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
    } else {
      favorites.add(restaurantDto);
    }

    userRepository.save(user);

    return restaurantDto;
  }

  @Override
  public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {

    Restaurant restaurant = findRestaurantById(restaurantId);
    restaurant.setOpen(!restaurant.isOpen());

    return restaurantRepository.save(restaurant);
  }
}
