package org.example.modules.auth;

import org.example.dto.*;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.modules.roles.RoleRepository;
import org.example.modules.users.UserRepository;
import org.example.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authManager,
                          JwtTokenProvider tokenProvider,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        var authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = tokenProvider.createToken(userDetails.getUsername(), authorities);

        LoginResponseDTO resp = new LoginResponseDTO();
        resp.setToken(token);
        return resp;
    }

    @PostMapping("/register")
    public LoginResponseDTO register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        var authorities = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Enum::name)
                .collect(Collectors.toList());

        String token = tokenProvider.createToken(user.getUsername(), authorities);

        LoginResponseDTO resp = new LoginResponseDTO();
        resp.setToken(token);
        return resp;
    }
}
