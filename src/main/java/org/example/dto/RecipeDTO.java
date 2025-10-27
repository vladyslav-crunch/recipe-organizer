package org.example.dto;

import lombok.Data;
import org.example.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecipeDTO {
    private String name;
    private String description;
    private int preparationTime;
    private String categoryName;
    private List<String> ingredientNames;

    public static RecipeDTO fromEntity(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setPreparationTime(recipe.getPreparationTime());
        dto.setCategoryName(recipe.getCategory() != null ? recipe.getCategory().getName() : null);
        dto.setIngredientNames(recipe.getIngredients() != null
                ? recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())
                : null);
        return dto;
    }

    public Recipe toEntity(Category category, List<Ingredient> ingredients) {
        return  Recipe.create(name, description, preparationTime, category, ingredients);
    }
}
