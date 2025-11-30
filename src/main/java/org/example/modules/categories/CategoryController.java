package org.example.modules.categories;

import org.example.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        Category category = service.getById(id);
        return category != null ? ResponseEntity.ok(category)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    public Category create(@RequestBody Category category) {
        return service.create(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public Category update(@PathVariable int id, @RequestBody Category category) {
        category.setId(id);
        return service.update(category);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
