package org.example.modules.categories;

import org.example.entity.Category;
import org.springframework.http.ResponseEntity;
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
        if (category != null) return ResponseEntity.ok(category);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.ok(service.create(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable int id, @RequestBody Category category) {
        category.setId(id);
        return ResponseEntity.ok(service.update(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("Category deleted");
    }
}
