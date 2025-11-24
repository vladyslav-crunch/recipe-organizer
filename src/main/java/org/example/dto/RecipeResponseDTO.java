package org.example.dto;

import lombok.Data;

@Data
public class RecipeResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private int preparationTime;
    private Integer userId;
    private String username;
    private Integer categoryId;
    private String categoryName;
}
