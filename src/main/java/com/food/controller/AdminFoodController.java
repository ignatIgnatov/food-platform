package com.food.controller;

import com.food.dto.request.CreateFoodRequest;
import com.food.dto.response.FoodResponseDto;
import com.food.dto.response.MessageResponse;
import com.food.model.Food;
import com.food.model.Restaurant;
import com.food.service.FoodService;
import com.food.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
@RequiredArgsConstructor
public class AdminFoodController {

    private final FoodService foodService;
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<FoodResponseDto> createFood(@RequestBody CreateFoodRequest request) {
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        FoodResponseDto food = foodService.createFood(request, request.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable("id") Long id) {
        foodService.deleteFood(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted successfully!");
        return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable("id") Long id) {
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
