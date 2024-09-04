package com.food.controller;

import com.food.dto.response.FoodResponseDto;
import com.food.model.Food;
import com.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name) {
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodResponseDto>> getRestaurantFoods(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonVeg,
            @RequestParam(required = false) String foodCategory) {
        List<FoodResponseDto> foods =
                foodService.getRestaurantsFood(restaurantId, vegetarian, nonVeg, seasonal, foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
