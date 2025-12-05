package org.example.modules.recipes;

import org.example.dto.RecipeRequestDTO;
import org.example.dto.RecipeResponseDTO;
import org.example.entity.Category;
import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.modules.categories.CategoryRepository;
import org.example.modules.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock private RecipeRepository repo;
    @Mock private UserRepository userRepo;
    @Mock private CategoryRepository categoryRepo;
    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    @InjectMocks
    private RecipeService service;

    @BeforeEach
    void setUpSecurity() {
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void clearSecurity() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAll_ShouldMapToDTOs() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        when(repo.findAll()).thenReturn(java.util.List.of(recipe));

        var result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void create_ShouldSaveRecipe_WhenUserAndCategoryExist() {
        RecipeRequestDTO dto = new RecipeRequestDTO();
        dto.setName("Pizza");
        dto.setCategoryId(10);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin");
        when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
        when(categoryRepo.findById(10)).thenReturn(Optional.of(new Category()));

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(55);
        savedRecipe.setName("Pizza");
        when(repo.save(any(Recipe.class))).thenReturn(savedRecipe);

        RecipeResponseDTO result = service.create(dto);

        assertEquals(55, result.getId());
        assertEquals("Pizza", result.getName());
    }

    @Test
    void create_ShouldThrowException_WhenUserNotFound() {
        RecipeRequestDTO dto = new RecipeRequestDTO();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("unknown");
        when(userRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.create(dto));
    }

    @Test
    void create_ShouldThrowException_WhenCategoryNotFound() {
        RecipeRequestDTO dto = new RecipeRequestDTO();
        dto.setCategoryId(999);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin");
        when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
        when(categoryRepo.findById(999)).thenReturn(Optional.empty());

        Exception e = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertTrue(e.getMessage().contains("Category not found"));
    }

    @Test
    void update_ShouldUpdateFields() {
        RecipeRequestDTO dto = new RecipeRequestDTO();
        dto.setName("New Name");

        Recipe existing = new Recipe();
        existing.setId(1);
        existing.setName("Old Name");

        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(Recipe.class))).thenReturn(existing);

        RecipeResponseDTO result = service.update(1, dto);

        assertEquals("New Name", result.getName());
    }

    @Test
    void update_ShouldUpdateCategory_WhenProvided() {
        RecipeRequestDTO dto = new RecipeRequestDTO();
        dto.setCategoryId(20);

        Recipe existing = new Recipe();
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(categoryRepo.findById(20)).thenReturn(Optional.of(new Category()));
        when(repo.save(any(Recipe.class))).thenReturn(existing);

        assertDoesNotThrow(() -> service.update(1, dto));
        verify(categoryRepo).findById(20);
    }

    @Test
    void delete_ShouldCallRepo() {
        service.delete(100);
        verify(repo).deleteById(100);
    }
}