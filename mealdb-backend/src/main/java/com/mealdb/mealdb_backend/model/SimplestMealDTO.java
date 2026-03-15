package com.mealdb.mealdb_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplestMealDTO {
    private String mealId;
    private String mealName;
    private String category;
    private String area;
    private int ingredientCount;
    private List<String> ingredients;
    private String thumbnail;
    private String youtubeLink;
    private String instructions;
}
