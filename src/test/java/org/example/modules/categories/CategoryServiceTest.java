package org.example.modules.categories;

import org.example.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;

    @Test
    void getAll_ShouldReturnListOfCategories() {
        Category c1 = new Category(); c1.setId(1);
        Category c2 = new Category(); c2.setId(2);
        when(repo.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Category> result = service.getAll();

        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnCategory_WhenExists() {
        Category category = new Category();
        category.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(category));

        Category result = service.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void getById_ShouldReturnNull_WhenNotExists() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        Category result = service.getById(99);

        assertNull(result);
    }

    @Test
    void create_ShouldSaveAndReturnCategory() {
        Category input = new Category();
        input.setName("Desery");
        when(repo.save(input)).thenReturn(input);

        Category result = service.create(input);

        assertEquals("Desery", result.getName());
        verify(repo, times(1)).save(input);
    }

    @Test
    void update_ShouldSaveAndReturnUpdatedCategory() {
        Category input = new Category();
        input.setId(1);
        input.setName("Obiady");
        when(repo.save(input)).thenReturn(input);

        Category result = service.update(input);

        assertEquals(1, result.getId());
        verify(repo).save(input);
    }

    @Test
    void delete_ShouldCallRepoDelete() {
        service.delete(5);

        verify(repo, times(1)).deleteById(5);
    }
}