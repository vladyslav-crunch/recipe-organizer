package org.example.exeption;

public class InvalidRecipeException extends Exception {
    public InvalidRecipeException(String message) {
        super(message);
    }
}