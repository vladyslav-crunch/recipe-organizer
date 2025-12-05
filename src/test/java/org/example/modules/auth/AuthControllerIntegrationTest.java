package org.example.modules.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.LoginRequestDTO;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.modules.roles.RoleRepository;
import org.example.modules.users.UserRepository;
import org.example.security.Permission;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AuthControllerIntegrationTest {

    @Autowired MockMvc mvc;
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired PasswordEncoder encoder;
    @Autowired ObjectMapper mapper;

    @BeforeAll
    void setup() {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role role = new Role();
            role.setName("ROLE_USER");
            role.setPermissions(Set.of(Permission.RECIPE_CREATE));
            roleRepository.save(role);
        }

        User u = new User();
        u.setUsername("testuser");
        u.setPassword(encoder.encode("pass"));
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow();
        u.setRoles(Set.of(role));
        userRepository.save(u);
    }

    @Test
    void login_WithCorrectCredentials_ReturnsJwtToken() throws Exception {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("testuser");
        req.setPassword("pass");

        mvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void login_WithWrongPassword_ReturnsUnauthorized() throws Exception {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("testuser");
        req.setPassword("wrongpass");

        mvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_WithNonExistentUser_ReturnsUnauthorized() throws Exception {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("ghostuser");
        req.setPassword("pass");

        mvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_NewUser_ReturnsJwtTokenAndSavesToDb() throws Exception {
        User u = new User();
        u.setUsername("newuser");
        u.setPassword("secret123");
        u.setEmail("new@example.com");

        mvc.perform(post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(u)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        User savedUser = userRepository.findByUsername("newuser").orElseThrow();
        assertNotEquals("secret123", savedUser.getPassword());
        assertTrue(savedUser.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
    }

    @Test
    void register_ExistingUsername_ReturnsError() throws Exception {
        User u = new User();
        u.setUsername("testuser");
        u.setPassword("anyPass");

        mvc.perform(post("/api/auth/register")
                        .with(csrf()) // <--- DODANO TOKEN CSRF
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(u)))
                .andExpect(status().is5xxServerError());
    }
}