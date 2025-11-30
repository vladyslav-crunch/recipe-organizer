package org.example.config;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.modules.roles.RoleRepository;
import org.example.modules.users.UserRepository;
import org.example.security.Permission;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Jeśli role istnieją, zakładamy że dane są już wgrane
        if (roleRepository.count() > 0) {
            return;
        }

        // Tworzymy ROLE_ADMIN
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setPermissions(Set.of(Permission.values()));
        roleRepository.save(adminRole);

        // Tworzymy ROLE_USER
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setPermissions(Set.of(
                Permission.RECIPE_CREATE,
                Permission.RECIPE_UPDATE,
                Permission.RECIPE_DELETE,
                Permission.CATEGORY_CREATE,
                Permission.CATEGORY_UPDATE,
                Permission.CATEGORY_DELETE
        ));
        roleRepository.save(userRole);

        // Tworzymy admina

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setActive(true);
        admin.setRoles(Set.of(adminRole));

        userRepository.save(admin);

        System.out.println(">>> Admin admin/admin123 został utworzony.");

        // Tworzymy admina

        User testUser = new User();
        testUser.setUsername("user");
        testUser.setEmail("user@example.com");
        testUser.setPassword(passwordEncoder.encode("user123"));
        testUser.setActive(true);
        testUser.setRoles(Set.of(userRole));

        userRepository.save(testUser);

        System.out.println(">>> User user/user123 został utworzony.");
    }
}
