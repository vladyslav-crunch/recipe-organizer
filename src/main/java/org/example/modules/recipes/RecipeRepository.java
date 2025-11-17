package org.example.modules.recipes;

import org.example.entity.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecipeRepository {

    private final JdbcTemplate jdbc;

    public RecipeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Recipe> recipeMapper = (rs, rowNum) -> {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt("id"));
        recipe.setName(rs.getString("name"));
        recipe.setDescription(rs.getString("description"));
        recipe.setPreparationTime(rs.getInt("preparation_time"));
        recipe.setUserId(rs.getInt("user_id"));
        recipe.setCategoryId(rs.getInt("category_id"));
        return recipe;
    };

    public List<Recipe> findAll() {
        String sql = "SELECT id, name, description, preparation_time, user_id, category_id FROM recipes";
        return jdbc.query(sql, recipeMapper);
    }

    public Optional<Recipe> findById(int id) {
        String sql = "SELECT id, name, description, preparation_time, user_id, category_id FROM recipes WHERE id = ?";
        List<Recipe> result = jdbc.query(sql, recipeMapper, id);
        return result.stream().findFirst();
    }

    public int save(Recipe recipe) {
        String sql = "INSERT INTO recipes (name, description, preparation_time, user_id, category_id) VALUES (?, ?, ?, ?, ?)";
        return jdbc.update(sql,
                recipe.getName(),
                recipe.getDescription(),
                recipe.getPreparationTime(),
                recipe.getUserId(),
                recipe.getCategoryId());
    }

    public int update(int id, Recipe recipe) {
        String sql = "UPDATE recipes SET name=?, description=?, preparation_time=?, user_id=?, category_id=? WHERE id=?";
        return jdbc.update(sql,
                recipe.getName(),
                recipe.getDescription(),
                recipe.getPreparationTime(),
                recipe.getUserId(),
                recipe.getCategoryId(),
                id);
    }

    public int deletePermanent(int id) {
        String sql = "DELETE FROM recipes WHERE id=?";
        return jdbc.update(sql, id);
    }
}