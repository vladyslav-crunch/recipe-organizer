package org.example;

import org.example.exeption.InvalidRecipeException;
import org.example.entity.*;
import org.example.service.RecipeXMLService;

import java.util.ArrayList;
import java.util.List;

public class XmlExample {
    public static void main(String[] args) {
        RecipeXMLService xmlService = new RecipeXMLService("recipes.xml");

        Category italian = new Category("Italian", "Traditional Italian cuisine");
        Category fastFood = new Category("Fast Food", "Fast but unhealthy");
        Category healthy = new Category("Healthy", "Healthy but not fast");
        Ingredient cheese = new Ingredient("Cheese", 100, "g");
        Ingredient dough = new Ingredient("Dough", 300, "g");
        Ingredient lettuce = new Ingredient("Lettuce", 200, "g");

        List<Recipe> recipes = new ArrayList<>();
        try {
            recipes.add(new Recipe("Pizza", "Classic Margherita", 25, italian,
                    new ArrayList<>(List.of(cheese, dough))));
            recipes.add(new Recipe("Burger", "Cheeseburger", 20, fastFood,
                    new ArrayList<>(List.of(cheese, lettuce))));
            recipes.add(new Recipe("Salad", "Fresh salad", 10, healthy,
                    new ArrayList<>(List.of(lettuce))));
        } catch (InvalidRecipeException e) {
            System.err.println("Error creating recipe: " + e.getMessage());
        }

        xmlService.saveRecipes(recipes);

        List<Recipe> loaded = xmlService.loadRecipes();
        System.out.println("\nLoaded recipes (valid only):");
        loaded.forEach(System.out::println);
    }
}
