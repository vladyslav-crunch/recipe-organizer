package org.example.modules.categories;

import org.example.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService service;

    @InjectMocks
    private CategoryController controller;

    @Test
    void getAll_ShouldReturnList() {
        when(service.getAll()).thenReturn(List.of(new Category()));
        List<Category> result = controller.getAll();
        assertFalse(result.isEmpty());
    }

    @Test
    void getById_ShouldReturnOk_WhenFound() {
        Category cat = new Category();
        when(service.getById(1)).thenReturn(cat);

        ResponseEntity<Category> response = controller.getById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cat, response.getBody());
    }

    @Test
    void getById_ShouldReturnNotFound_WhenNull() {
        when(service.getById(1)).thenReturn(null);
        ResponseEntity<Category> response = controller.getById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create_ShouldReturnCategory() {
        Category cat = new Category();
        when(service.create(cat)).thenReturn(cat);
        Category result = controller.create(cat);
        assertNotNull(result);
    }
}