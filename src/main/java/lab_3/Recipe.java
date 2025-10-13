package lab_3;
import java.io.Serializable;

public class Recipe implements Serializable {
    private String name;
    private String category;
    private int cookingTime;

    public Recipe() {}
    public Recipe(String name, String category, int cookingTime) throws InvalidRecipeException {
        if (name == null || name.isBlank()) {
            throw new InvalidRecipeException("Nazwa przepisu nie moze byc pusta");
        }
        if (cookingTime <= 0) {
            throw new InvalidRecipeException("Czas gotowania musi być dodatni");
        }
        this.name = name;
        this.category = category;
        this.cookingTime = cookingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public String toString() {
        return "Recipe{name='" + name + "', category='" + category + "', cookingTime=" + cookingTime + " min}";
    }
}
