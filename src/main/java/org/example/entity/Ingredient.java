package org.example.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Ingredient {
    @NonNull
    private String name;

    private double quantity;
    private String unit;
}
