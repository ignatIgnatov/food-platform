package com.food.controller;

import com.food.dto.response.UserResponseDto;
import com.food.model.Category;
import com.food.model.User;
import com.food.service.CategoryService;
import com.food.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final UserService userService;

  @PostMapping("/admin/category")
  public ResponseEntity<Category> createCategory(
      @RequestBody Category category, @RequestHeader("Authorization") String jwt) {
    UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
    User user = userService.findUserByEmail(userResponseDto.getEmail());
    Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  @GetMapping("/category/restaurant")
  public ResponseEntity<List<Category>> getRestaurantCategory(
      @RequestHeader("Authorization") String jwt) {
    UserResponseDto userResponseDto = userService.findUserByJwtToken(jwt);
    User user = userService.findUserByEmail(userResponseDto.getEmail());
    List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }
}
