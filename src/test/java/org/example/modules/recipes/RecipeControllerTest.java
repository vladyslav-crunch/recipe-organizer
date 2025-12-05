package org.example.modules.recipes;

import org.example.dto.RecipeResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeService service;

    @InjectMocks
    private RecipeController controller;

    @Test
    void getById_ShouldReturn200_WhenExists() {
        when(service.getById(1)).thenReturn(Optional.of(new RecipeResponseDTO()));

        ResponseEntity<RecipeResponseDTO> response = controller.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getById_ShouldReturn404_WhenEmpty() {
        when(service.getById(1)).thenReturn(Optional.empty());

        ResponseEntity<RecipeResponseDTO> response = controller.getById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete_ShouldExecute() {
        assertDoesNotThrow(() -> controller.delete(5));
        verify(service).delete(5);
    }
}