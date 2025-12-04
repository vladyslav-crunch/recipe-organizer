package org.example.modules.recipes;

import org.example.dto.RecipeRequestDTO;
import org.example.dto.RecipeResponseDTO;
import org.example.entity.Category;
import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.modules.categories.CategoryRepository;
import org.example.modules.users.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository repo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public RecipeService(RecipeRepository repo, UserRepository userRepo, CategoryRepository categoryRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    public List<RecipeResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<RecipeResponseDTO> getById(int id) {
        return repo.findById(id).map(this::toResponseDTO);
    }

    public RecipeResponseDTO create(RecipeRequestDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setPreparationTime(dto.getPreparationTime());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        recipe.setUser(user);

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        recipe.setCategory(category);

        return toResponseDTO(repo.save(recipe));
    }

    public RecipeResponseDTO update(int id, RecipeRequestDTO dto) {
        Recipe recipe = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setPreparationTime(dto.getPreparationTime());

        // Only allow updating category, user stays same
        if (dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            recipe.setCategory(category);
        }

        return toResponseDTO(repo.save(recipe));
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    private RecipeResponseDTO toResponseDTO(Recipe recipe) {
        RecipeResponseDTO dto = new RecipeResponseDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setPreparationTime(recipe.getPreparationTime());

        if (recipe.getUser() != null) {
            dto.setUserId(recipe.getUser().getId());
            dto.setUsername(recipe.getUser().getUsername());
        }

        if (recipe.getCategory() != null) {
            dto.setCategoryId(recipe.getCategory().getId());
            dto.setCategoryName(recipe.getCategory().getName());
        }

        return dto;
    }
}