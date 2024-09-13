package com.food.controller;

import com.food.dto.request.IngredientCategoryRequestDto;
import com.food.dto.request.IngredientItemRequestDto;
import com.food.dto.response.IngredientCategoryResponseDto;
import com.food.dto.response.IngredientItemResponseDto;
import com.food.model.IngredientsItem;
import com.food.service.IngredientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategoryResponseDto> createIngredientCategory(
            @RequestBody IngredientCategoryRequestDto request) {
        IngredientCategoryResponseDto ingredientCategory = ingredientsService.createIngredientCategory(request);
        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping("/item")
    public ResponseEntity<IngredientItemResponseDto> createIngredientItem(
            @RequestBody IngredientItemRequestDto request) {
        IngredientItemResponseDto ingredientsItem =
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
    public ResponseEntity<List<IngredientItemResponseDto>> getRestaurantIngredients(
            @PathVariable("id") Long id) {
        List<IngredientItemResponseDto> ingredientsItems = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(ingredientsItems, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategoryResponseDto>> getRestaurantIngredientCategory(
            @PathVariable("id") Long id) {
        List<IngredientCategoryResponseDto> ingredientCategories =
                ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }
}
