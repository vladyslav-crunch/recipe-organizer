package org.example.entity;

import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Recipe entity with validation logic and Lombok annotations.
 * Serializable — so it can be easily saved/loaded (e.g., to a file).
 */
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

    // Private constructor used internally by factory
    private Recipe(String name, String description, int preparationTime,
                   Category category, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.category = category;
        this.ingredients = ingredients;
    }

    /**
     * Static factory method that handles validation internally.
     * Returns null if validation fails, or the valid Recipe instance.
     */
    public static Recipe create(String name, String description, int preparationTime,
                                Category category, List<Ingredient> ingredients) {
        if (name == null || name.isBlank()) {
            System.err.println("Recipe creation failed: name cannot be empty");
            return null;
        }
        if (preparationTime <= 0) {
            System.err.println("Recipe creation failed: preparation time must be positive");
            return null;
        }
        return new Recipe(name, description, preparationTime, category, ingredients);
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
