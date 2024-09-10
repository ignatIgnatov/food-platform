package com.food.service.impl;

import com.food.dto.request.CreateRestaurantRequestDto;
import com.food.dto.response.CreateRestaurantResponseDto;
import com.food.dto.response.RestaurantResponseDto;
import com.food.exception.restaurant.RestaurantNotFoundException;
import com.food.model.Address;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.repository.AddressRepository;
import com.food.repository.RestaurantRepository;
import com.food.repository.UserRepository;
import com.food.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final ModelMapper modelMapper;

    @Override
    public CreateRestaurantResponseDto createRestaurant(CreateRestaurantRequestDto restaurantRequestDto, User user) {

        Address address = addressRepository.save(restaurantRequestDto.getAddress());

        Restaurant restaurant = modelMapper.map(restaurantRequestDto, Restaurant.class);
        restaurant.setAddress(address);
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurantRepository.save(restaurant);

        return modelMapper.map(restaurant, CreateRestaurantResponseDto.class);
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
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantRepository
                .findAll()
                .stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantResponseDto.class))
                .toList();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new RestaurantNotFoundException(messageSource)
        );
        return modelMapper.map(restaurant, RestaurantResponseDto.class);
    }

    @Override
    public RestaurantResponseDto getRestaurantByUserId(Long userId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if (restaurant == null) {
            throw new RestaurantNotFoundException(messageSource, userId);
        }
        return modelMapper.map(restaurant, RestaurantResponseDto.class);
    }

    @Override
    public CreateRestaurantResponseDto addToFavorite(Long restaurantId, User user) {

        Restaurant restaurant = findRestaurantById(restaurantId);

        CreateRestaurantResponseDto createRestaurantResponseDto = modelMapper.map(restaurant, CreateRestaurantResponseDto.class);

        boolean isFavorite = false;
        List<CreateRestaurantResponseDto> favorites = user.getFavorites();
        for (CreateRestaurantResponseDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(createRestaurantResponseDto);
        }

        user.setFavorites(favorites);
        userRepository.save(user);

        return createRestaurantResponseDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) {

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(
                () -> new RestaurantNotFoundException(messageSource)
        );
    }
}
