package org.example.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Scraper {
    public static void main(String[] args) {
        ParserService parserService = new ParserService();
        try {
            HashMap<String, ArrayList<String>> employeesByUnit = parserService.getResearchUnitsWithEmployees();

            for (Map.Entry<String, ArrayList<String>> entry : employeesByUnit.entrySet()) {
                System.out.println("\n " + entry.getKey() + ":");
                for (String employee : entry.getValue()) {
                    System.out.println("   - " + employee);
                }
            }
        } catch (Exception e) {
            System.err.println("Wystąpił błąd podczas wykonywania Zadania 10.1: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Odznaliezione pracowniki ze stopniem dr lub dr inż:");

        try {
            List<Employee> allEmployees = parserService.getEmployees();
            List<Employee> filteredEmployees = new ArrayList<>();

            for (Employee e : allEmployees) {
                String degree = e.getAcademicDegree();
                if (degree.equals("dr") || degree.equals("dr inż.")) {
                    filteredEmployees.add(e);
                }
            }

            System.out.printf("Znaleziono %d pracowników ze stopniem dr lub dr inż.:\n", filteredEmployees.size());
            for (Employee e : filteredEmployees) {
                System.out.println("   - " + e.toString());
            }

        } catch (Exception e) {
            System.err.println("Wystąpił błąd podczas wykonywania Zadania 10.2: " + e.getMessage());
        }


        System.out.println();
        System.out.println("Aktualnosci zwiazane z godzinami rektorskimi lub dziekanskimi na stronie WEiI:");
        try {
            ArrayList<Holiday> holidays = parserService.getHolidays();

            if (holidays.isEmpty()) {
                System.out.println("Nie znaleziono żadnych aktualnych informacji o Dniach/Godzinach Rektorskich.");
            } else {
                for (Holiday h : holidays) {
                    System.out.println("   - " + h.toString());
                }
            }
        } catch (Exception e) {
            System.err.println("Wystąpił błąd podczas wykonywania Zadania 10.3: " + e.getMessage());
        }
    }
}