package com.mealdb.mealdb_backend.controller;

import com.mealdb.mealdb_backend.model.SimplestMealDTO;
import com.mealdb.mealdb_backend.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/simplest")
    public ResponseEntity<?> getSimplestMeal(
            @RequestParam(defaultValue = "pasta") String name) {

        SimplestMealDTO result = mealService.getSimplestMeal(name);

        if (result == null) {
            return ResponseEntity.status(404).body("No meals found for: " + name);
        }
        return ResponseEntity.ok(result);
    }
}
