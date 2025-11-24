package org.example.modules.categories;
import org.example.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Category create(Category category) {
        return repo.save(category);
    }

    public Category update(Category category) {
        return repo.save(category);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
