package org.example.modules.recipes;

import org.example.entity.Recipe;
import org.example.entity.User;
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
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("user_name"));
        user.setEmail(rs.getString("user_email"));

        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt("id"));
        recipe.setName(rs.getString("name"));
        recipe.setDescription(rs.getString("description"));
        recipe.setPreparationTime(rs.getInt("preparation_time"));
        recipe.setUser(user);
        return recipe;
    };

    public List<Recipe> findAll() {
        String sql = """
            SELECT r.id, r.name, r.description, r.preparation_time, r.user_id,
                   u.username AS user_name, u.email AS user_email
            FROM recipes r
            LEFT JOIN users u ON r.user_id = u.id
            """;
        return jdbc.query(sql, recipeMapper);
    }

    public Optional<Recipe> findById(int id) {
        String sql = """
            SELECT r.id, r.name, r.description, r.preparation_time, r.user_id,
                   u.username AS user_name, u.email AS user_email
            FROM recipes r
            LEFT JOIN users u ON r.user_id = u.id
            WHERE r.id = ?
            """;
        List<Recipe> result = jdbc.query(sql, recipeMapper, id);
        return result.stream().findFirst();
    }

    public int save(Recipe recipe) {
        String sql = "INSERT INTO recipes (name, description, preparation_time, user_id) VALUES (?, ?, ?, ?)";
        return jdbc.update(sql,
                recipe.getName(),
                recipe.getDescription(),
                recipe.getPreparationTime(),
                recipe.getUser() != null ? recipe.getUser().getId() : null
        );
    }

    public int update(int id, Recipe recipe) {
        String sql = "UPDATE recipes SET name=?, description=?, preparation_time=? WHERE id=?";
        return jdbc.update(sql, recipe.getName(), recipe.getDescription(), recipe.getPreparationTime(), id);
    }

    public int deletePermanent(int id) {
        String sql = "DELETE FROM recipes WHERE id=?";
        return jdbc.update(sql, id);
    }
}