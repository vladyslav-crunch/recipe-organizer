package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class StreamExample {

    public static void main(String[] args) throws IOException {
        //Zadanie 1

        List<String> recipes = List.of(
                "Pizza", "Spaghetti", "Burger", "Pizza", "Salad", "Burger", "Soup"
        );


        System.out.println("=== FILTROWANIE ===");
        recipes.stream()
                .filter(recipe -> recipe.startsWith("S"))
                .forEach(System.out::println);

        //Zadanie 2

        System.out.println("\n=== LICZENIE POWTÓRZEŃ ===");
        Map<String, Long> counts = recipes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        counts.forEach((key, value) ->
                System.out.println(key + " występuje " + value + " raz/y"));

        //Zadanie 3

        System.out.println("\n=== MAPOWANIE ===");
        List<String> upperRecipes = recipes.stream()
                .map(String::toUpperCase)
                .toList();
        upperRecipes.forEach(System.out::println);

        //Zadanie 4

        System.out.println("\n=== ZAPIS I ODCZYT Z PLIKU ===");
        Path filePath = Paths.get("recipes.txt");

        // Zapis (java.io)

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (String recipe : recipes) {
                writer.write(recipe);
                writer.newLine();
            }
        }

        // Odczyt (java.nio)

        List<String> readRecipes = Files.readAllLines(filePath);
        System.out.println("Odczytano z pliku:");
        readRecipes.forEach(System.out::println);

        // Zadanie 5

        System.out.println("\n=== FILTROWANIE I SORTOWANIE Z PLIKU ===");
        List<String> filteredSorted = readRecipes.stream()
                .filter(r -> r.length() > 5)
                .sorted()
                .toList();

        System.out.println("Po filtrowaniu i sortowaniu:");
        filteredSorted.forEach(System.out::println);
    }
}
