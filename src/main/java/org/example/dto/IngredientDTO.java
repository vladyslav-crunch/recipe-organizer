package org.example.dto;

import lombok.Data;
import org.example.entity.Ingredient;

@Data
public class IngredientDTO {
    private String name;
    private double quantity;
    private String unit;

    public static IngredientDTO fromEntity(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setName(ingredient.getName());
        dto.setQuantity(ingredient.getQuantity());
        dto.setUnit(ingredient.getUnit());
        return dto;
    }

    public Ingredient toEntity() {
        return new Ingredient(name, quantity, unit);
    }
}
