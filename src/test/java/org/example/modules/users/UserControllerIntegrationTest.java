package org.example.modules.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.*;
import org.example.modules.roles.RoleRepository;
import org.example.security.JwtTokenProvider;
import org.example.security.Permission;
import org.junit.jupiter.api.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider jwt;

    String token;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        if (!userRepository.existsByUsername("admin")) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            role.setPermissions(Set.of(
                    Permission.USER_VIEW,
                    Permission.USER_CREATE,
                    Permission.USER_UPDATE,
                    Permission.USER_DELETE
            ));
            roleRepository.save(role);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("pass"));
            admin.setRoles(Set.of(role));
            userRepository.save(admin);

            token = jwt.createToken("admin",
                    role.getPermissions().stream().map(Enum::name).toList()
            );
        }
    }

    @Test
    void getAll_ForbiddenWithoutToken() throws Exception {
        mvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll_Returns200WithAuth() throws Exception {
        mvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_WorksWithPermission() throws Exception {
        User u = new User();
        u.setUsername("john");
        u.setPassword("pass");

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(u))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getNotExistingUser_ReturnsNotFound() throws Exception {
        mvc.perform(get("/api/users/123")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_WorksWithPermission() throws Exception {
        User u = new User();
        u.setUsername("john");
        u.setPassword("pass");

        mvc.perform(put("/api/users/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(u))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}