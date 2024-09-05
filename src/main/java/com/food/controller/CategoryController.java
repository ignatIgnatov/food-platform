package com.food.controller;

import com.food.dto.response.CategoryResponseDto;
import com.food.dto.response.UserResponseDto;
import com.food.model.Category;
import com.food.model.User;
import com.food.service.CategoryService;
import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryResponseDto> createCategory(
            @RequestBody Category category, @RequestHeader("Authorization") String jwt) {
        UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
        User user = userService.findUserByEmail(userResponseDto.getEmail());
        CategoryResponseDto createdCategory = categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant/{restaurantId}")
    public ResponseEntity<List<CategoryResponseDto>> getRestaurantCategory(
            @PathVariable("restaurantId") Long restaurantId) {
        List<CategoryResponseDto> categories = categoryService.findCategoryByRestaurantId(restaurantId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
