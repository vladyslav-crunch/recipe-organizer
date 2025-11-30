package org.example.dto;

import lombok.Data;

@Data
public class RecipeRequestDTO {
    private String name;
    private String description;
    private int preparationTime;
    private Integer categoryId;
}
