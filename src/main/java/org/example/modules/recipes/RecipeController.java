package org.example.modules.recipes;

import org.example.dto.RecipeRequestDTO;
import org.example.dto.RecipeResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecipeResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> getById(@PathVariable int id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('RECIPE_CREATE')")
    public RecipeResponseDTO create(@RequestBody RecipeRequestDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('RECIPE_UPDATE')")
    public RecipeResponseDTO update(@PathVariable int id, @RequestBody RecipeRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('RECIPE_DELETE')")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
