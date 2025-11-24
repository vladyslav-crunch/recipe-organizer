package org.example.modules.users;

import org.example.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public Optional<User> getById(int id) {
        return repo.findById(id);
    }

    public User create(User user) {
        return repo.save(user);
    }

    public User update(int id, User user) {
        user.setId(id);
        return repo.save(user);
    }

    public void deactivate(int id) {
        repo.findById(id).ifPresent(u -> {
            u.setActive(false);
            repo.save(u);
        });
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
