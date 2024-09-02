package com.food.controller;

import com.food.dto.request.CreateRestaurantRequestDto;
import com.food.dto.response.MessageResponse;
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

@RestController
@RequestMapping("/api/admin/restaurants")
@RequiredArgsConstructor
public class AdminRestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(
            @RequestBody CreateRestaurantRequestDto restaurantRequestDto,
            @RequestHeader("Authorization") String jwt) {
        UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
        User user = userService.findUserByEmail(userResponseDto.getEmail());
        RestaurantResponseDto restaurant = restaurantService.createRestaurant(restaurantRequestDto, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequestDto restaurantRequestDto, @PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.updateRestaurant(id, restaurantRequestDto);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@PathVariable("id") Long id) {
        restaurantService.deleteRestaurant(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant deleted successfully!");
        return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@PathVariable("userId") Long userId) {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
