package org.example.builder;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MealPlan {
    private LocalDate date;
    private List<String> recipes;
    private String notes;
}
