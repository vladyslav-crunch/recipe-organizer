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

    public void create(User user) {
        repo.save(user);
    }

    public void update(int id, User user) {
        repo.update(id, user);
    }

    public void deactivate(int id) {
        repo.softDelete(id);
    }

    public void delete(int id) {
        repo.deletePermanent(id);
    }
}