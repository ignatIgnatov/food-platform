package com.food.controller;

import com.food.dto.request.IngredientCategoryRequestDto;
import com.food.dto.request.IngredientItemRequestDto;
import com.food.model.IngredientCategory;
import com.food.model.IngredientsItem;
import com.food.service.IngredientsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientController {

  private final IngredientsService ingredientsService;

  @PostMapping("/category")
  public ResponseEntity<IngredientCategory> createIngredientCategory(
      @RequestBody IngredientCategoryRequestDto request) {
    IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(request);
    return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
  }

  @PostMapping("/item")
  public ResponseEntity<IngredientsItem> createIngredientItem(
      @RequestBody IngredientItemRequestDto request) {
    IngredientsItem ingredientsItem =
        ingredientsService.createIngredientItem(
            request.getRestaurantId(), request.getName(), request.getCategoryId());
    return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
  }

  @PutMapping("/{id}/stock")
  public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable("id") Long id) {
    IngredientsItem ingredientsItem = ingredientsService.updateStock(id);
    return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(
      @PathVariable("id") Long id) {
    List<IngredientsItem> ingredientsItems = ingredientsService.findRestaurantIngredients(id);
    return new ResponseEntity<>(ingredientsItems, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{id}/category")
  public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
      @PathVariable("id") Long id) {
    List<IngredientCategory> ingredientCategories =
        ingredientsService.findIngredientCategoryByRestaurantId(id);
    return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
  }
}
