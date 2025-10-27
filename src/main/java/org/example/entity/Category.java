package org.example.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {
    @NonNull
    private String name;

    private String description;
}
