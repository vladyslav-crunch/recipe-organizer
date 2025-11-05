package org.example.entity;

import lombok.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {

    private int id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private boolean active = true;

    private List<Recipe> recipes;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}