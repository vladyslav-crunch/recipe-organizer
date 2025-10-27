package org.example.builder;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ShoppingList {
    private String title;
    private List<String> items;
    private boolean completed;
}
