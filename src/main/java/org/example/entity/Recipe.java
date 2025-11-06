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
    private int id;
    private String description;
    private int preparationTime;
    private List<Ingredient> ingredients;
    private User user;

    public Recipe(String name, String description, int preparationTime, List<Ingredient> ingredients, User user)
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
        this.ingredients = ingredients;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Recipe{name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", preparationTime=" + preparationTime + " min" +
                ", ingredients=" + (ingredients != null ? ingredients.size() : 0) +
                ", user=" + (user != null ? user.getUsername() : "brak") +
                '}';
    }
}
