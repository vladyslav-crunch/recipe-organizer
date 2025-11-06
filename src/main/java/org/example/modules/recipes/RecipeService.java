package org.example.modules.recipes;

import org.example.entity.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository repo;

    public RecipeService(RecipeRepository repo) {
        this.repo = repo;
    }

    public List<Recipe> getAll() {
        return repo.findAll();
    }

    public Optional<Recipe> getById(int id) {
        return repo.findById(id);
    }

    public void create(Recipe recipe) {
        repo.save(recipe);
    }

    public void update(int id, Recipe recipe) {
        repo.update(id, recipe);
    }

    public void delete(int id) {
        repo.deletePermanent(id);
    }
}