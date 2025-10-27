package lab_3;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlExample {
    private static final Logger LOGGER = Logger.getLogger(XmlExample.class.getName());

    public static void main(String[] args) {
        String fileName = "recipes.xml";

        try {
            List<Recipe> recipes = new ArrayList<>();
            recipes.add(new Recipe("Pizza", "Italian", 30));
            recipes.add(new Recipe("Burger", "Fast Food", 20));
            recipes.add(new Recipe("Salad", "Healthy", 10));

            try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
                encoder.writeObject(recipes);
                System.out.println("Dane zapisano do pliku XML: " + fileName);
            }

            List<Recipe> readRecipes = new ArrayList<>();
            try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))) {
                Object obj = decoder.readObject();
                if (obj instanceof List<?>) {
                    for (Object o : (List<?>) obj) {
                        if (o instanceof Recipe) {
                            readRecipes.add((Recipe) o);
                        }
                    }
                }
            }

            System.out.println("\nOdczytane przepisy:");
            for (Recipe recipe : readRecipes) {
                System.out.println(recipe);
            }

            System.out.println("\nWalidacja odczytanych danych:");
            for (Recipe recipe : readRecipes) {
                try {
                    new Recipe(recipe.getName(), recipe.getCategory(), recipe.getCookingTime());
                    System.out.println("Poprawny przepis: " + recipe);
                } catch (InvalidRecipeException e) {
                    System.out.println("Błąd: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("Wystąpił błąd: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Unexpected error", e);
        }
    }
}