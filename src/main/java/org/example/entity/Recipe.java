package org.example.entity;

import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipe {
    private Integer id;

    @NonNull
    private String name;
    private String description;
    private int preparationTime;

    @NonNull
    private Integer userId;

    @NonNull
    private Integer categoryId;

}

