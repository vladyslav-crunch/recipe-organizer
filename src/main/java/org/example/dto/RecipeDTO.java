package org.example.dto;

import org.example.exeption.InvalidRecipeException;
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
    private User user;

    public static RecipeDTO fromEntity(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setPreparationTime(recipe.getPreparationTime());
        dto.setIngredientNames(recipe.getIngredients() != null
                ? recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())
                : null);
        dto.setUser(recipe.getUser());
        return dto;
    }

    public Recipe toEntity(Category category, List<Ingredient> ingredients) throws InvalidRecipeException {
        return new Recipe(name, description, preparationTime, ingredients, user);
    }
}
