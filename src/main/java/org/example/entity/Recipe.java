package org.example.entity;

import org.example.exeption.InvalidRecipeException;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipe implements Serializable {

    @NonNull
    private String name;

    private String description;
    private int preparationTime;
    private Category category;
    private List<Ingredient> ingredients;

    public Recipe(String name, String description, int preparationTime,
                  Category category, List<Ingredient> ingredients)
            throws InvalidRecipeException {

        if (name == null || name.isBlank()) {
            throw new InvalidRecipeException("Nazwa przepisu nie może być pusta");
        }
        if (preparationTime <= 0) {
            throw new InvalidRecipeException("Czas przygotowania musi być dodatni");
        }

        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.category = category;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", preparationTime=" + preparationTime + " min" +
                ", category=" + (category != null ? category.getName() : "brak") +
                ", ingredients=" + (ingredients != null ? ingredients.size() : 0) +
                '}';
    }
}
