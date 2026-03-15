package com.mealdb.mealdb_backend.service;

import com.mealdb.mealdb_backend.model.Meal;
import com.mealdb.mealdb_backend.model.MealApiResponse;
import com.mealdb.mealdb_backend.model.SimplestMealDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${mealdb.api.base-url}")
    private String baseUrl;

    public SimplestMealDTO getSimplestMeal(String searchTerm) {
        String url = baseUrl + "/search.php?s=" + searchTerm;
        MealApiResponse response = restTemplate.getForObject(url, MealApiResponse.class);

        if (response == null || response.getMeals() == null || response.getMeals().isEmpty()) {
            return null;  // handled by controller as 404
        }

        Meal simplestMeal = null;
        int minCount = Integer.MAX_VALUE;

        for (Meal meal : response.getMeals()) {
            int count = countIngredients(meal);
            if (count < minCount) {
                minCount = count;
                simplestMeal = meal;
            }
        }

        return buildDTO(simplestMeal, minCount);
    }

    private int countIngredients(Meal meal) {
        int count = 0;
        try {
            for (int i = 1; i <= 20; i++) {
                Field field = Meal.class.getDeclaredField("strIngredient" + i);
                field.setAccessible(true);
                String value = (String) field.get(meal);
                if (value != null && !value.trim().isEmpty()) {
                    count++;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    private List<String> extractIngredients(Meal meal) {
        List<String> ingredients = new ArrayList<>();
        try {
            for (int i = 1; i <= 20; i++) {
                Field ingField  = Meal.class.getDeclaredField("strIngredient" + i);
                Field measField = Meal.class.getDeclaredField("strMeasure" + i);
                ingField.setAccessible(true);
                measField.setAccessible(true);

                String ingredient = (String) ingField.get(meal);
                String measure    = (String) measField.get(meal);

                if (ingredient != null && !ingredient.trim().isEmpty()) {
                    String entry = (measure != null && !measure.trim().isEmpty())
                            ? measure.trim() + " " + ingredient.trim()
                            : ingredient.trim();
                    ingredients.add(entry);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    private SimplestMealDTO buildDTO(Meal meal, int count) {
        return new SimplestMealDTO(
                meal.getIdMeal(),
                meal.getStrMeal(),
                meal.getStrCategory(),
                meal.getStrArea(),
                count,
                extractIngredients(meal),
                meal.getStrMealThumb(),
                meal.getStrYoutube(),
                meal.getStrInstructions()
        );
    }
}
