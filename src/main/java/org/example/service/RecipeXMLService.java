package org.example.service;

import org.example.entity.User;
import org.example.exeption.InvalidRecipeException;
import org.example.entity.Recipe;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeXMLService {
//    private static final Logger LOGGER = Logger.getLogger(RecipeXMLService.class.getName());
//    private final String fileName;
//
//    public RecipeXMLService(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public void saveRecipes(List<Recipe> recipes) {
//        try (XMLEncoder encoder = new XMLEncoder(
//                new BufferedOutputStream(new FileOutputStream(fileName)))) {
//            encoder.writeObject(recipes);
//            System.out.println("Recipes saved to XML: " + fileName);
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error saving recipes to XML", e);
//        }
//    }
//
//
//    public List<Recipe> loadRecipes() {
//        File file = new File(fileName);
//        if (!file.exists()) {
//            System.out.println("No XML file found, returning empty list");
//            return new ArrayList<>();
//        }
//
//        List<Recipe> validRecipes = new ArrayList<>();
//        User user1 = new User();
//
//        try (XMLDecoder decoder = new XMLDecoder(
//                new BufferedInputStream(new FileInputStream(fileName)))) {
//
//            Object obj = decoder.readObject();
//            if (obj instanceof List<?>) {
//                for (Object o : (List<?>) obj) {
//                    if (o instanceof Recipe recipe) {
//                        try {
//                            new Recipe(recipe.getName(),
//                                    recipe.getDescription(),
//                                    recipe.getPreparationTime(),
//                                    recipe.getIngredients(),
//                                    user1);
//                            validRecipes.add(recipe);
//                        } catch (InvalidRecipeException e) {
//                            LOGGER.log(Level.WARNING,
//                                    "Skipping invalid recipe: " + recipe.getName() +
//                                            " (" + e.getMessage() + ")");
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Error reading recipes from XML", e);
//        }
//        return validRecipes;
//    }
}
