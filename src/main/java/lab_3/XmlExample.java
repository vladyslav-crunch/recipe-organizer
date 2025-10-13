package lab_3;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlExample {

    public static void main(String[] args) {
        String fileName = "recipes.xml";

        try {
            // === Tworzenie przykładowych danych ===
            List<Recipe> recipes = new ArrayList<>();
            recipes.add(new Recipe("Pizza", "Italian", 30));
            recipes.add(new Recipe("Burger", "Fast Food", 20));
            recipes.add(new Recipe("Salad", "Healthy", 10));

            // === Zapis do XML ===
            try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
                encoder.writeObject(recipes);
                System.out.println("Dane zapisano do pliku XML: " + fileName);
            }

            // === Odczyt z XML ===
            List<Recipe> readRecipes;
            try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))) {
                readRecipes = (List<Recipe>) decoder.readObject();
            }

            System.out.println("\nOdczytane przepisy:");
            for (Recipe recipe : readRecipes) {
                System.out.println(recipe);
            }

            // === Walidacja z wyjątkami ===
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
            e.printStackTrace();
        }
    }
}