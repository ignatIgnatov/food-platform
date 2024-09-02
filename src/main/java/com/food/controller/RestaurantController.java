package com.food.controller;

import com.food.dto.response.RestaurantResponseDto;
import com.food.dto.response.UserResponseDto;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.service.RestaurantService;
import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestParam String keyword) {
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        List<RestaurantResponseDto> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-to-favorites")
    public ResponseEntity<RestaurantResponseDto> addToFavorites(
            @RequestHeader("Authorization") String jwt, @PathVariable("id") Long id) {
        UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
        User user = userService.findUserByEmail(userResponseDto.getEmail());
        RestaurantResponseDto restaurant = restaurantService.addToFavorite(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
