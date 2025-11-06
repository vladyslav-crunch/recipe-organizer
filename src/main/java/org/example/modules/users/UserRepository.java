package org.example.modules.users;

import org.example.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<User> mapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setActive(rs.getBoolean("active"));
        return user;
    };

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbc.query(sql, mapper);
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> list = jdbc.query(sql, mapper, id);
        return list.stream().findFirst();
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, email, password, active) VALUES (?, ?, ?, TRUE)";
        jdbc.update(sql, user.getUsername(), user.getEmail(), user.getPassword());
    }

    public void update(int id, User user) {
        String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        jdbc.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), id);
    }

    public void softDelete(int id) {
        String sql = "UPDATE users SET active = FALSE WHERE id = ?";
        jdbc.update(sql, id);
    }

    public void deletePermanent(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbc.update(sql, id);
    }
}