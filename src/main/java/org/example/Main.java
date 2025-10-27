package org.example;

import org.example.builder.MealPlan;
import org.example.builder.ShoppingList;
import org.example.builder.User;
import org.example.dto.RecipeDTO;
import org.example.entity.Category;
import org.example.entity.Ingredient;
import org.example.entity.Recipe;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
            System.out.println("=== 🔹 TEST ENCJI ===");

            Category category = new Category("Italian", "Traditional Italian cuisine");
            Ingredient cheese = new Ingredient("Cheese", 100, "g");
            Ingredient dough = new Ingredient("Dough", 300, "g");


            Recipe recipe =  Recipe.create("Pizza", "Margherita with cheese and tomato", 10, category, List.of(cheese, dough));
            System.out.println(recipe);

            System.out.println("\n=== 🔹 TEST BUILDERÓW ===");

            MealPlan plan = MealPlan.builder()
                    .date(LocalDate.now())
                    .recipes(List.of("Pizza", "Pasta"))
                    .notes("Dinner ideas for the week")
                    .build();
            System.out.println(plan);

            ShoppingList shoppingList = ShoppingList.builder()
                    .title("Weekend Shopping")
                    .items(List.of("Tomatoes", "Cheese", "Olive Oil"))
                    .completed(false)
                    .build();
            System.out.println(shoppingList);

            User user = User.builder()
                    .username("chefMario")
                    .email("mario@kitchen.com")
                    .password("supersecret")
                    .build();
            System.out.println(user);

            System.out.println("\n=== 🔹 TEST DTO ===");

            // Mapowanie encji → DTO
            RecipeDTO dto = RecipeDTO.fromEntity(recipe);
            System.out.println("Mapped DTO: " + dto);

            // Przywrócenie stanu encji z DTO
            Recipe restoredRecipe = dto.toEntity(category, List.of(cheese, dough));
            System.out.println("Restored Entity: " + restoredRecipe);

            // Sprawdzenie równości
            System.out.println("\nDTO equals original? " + recipe.equals(restoredRecipe));

}}