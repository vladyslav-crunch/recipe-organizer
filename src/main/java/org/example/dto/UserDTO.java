package org.example.dto;

import lombok.Data;
import org.example.entity.User;

@Data
public class UserDTO {

    private String username;
    private String email;
    private String password;
    private boolean active;

    // Convert from entity to DTO
    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword()); // optional — omit if you don’t want to expose it
        dto.setActive(user.isActive());
        return dto;
    }

    // Convert from DTO to entity
    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(active);
        return user;
    }
}