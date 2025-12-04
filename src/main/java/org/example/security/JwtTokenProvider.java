package org.example.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long validityInMilliseconds;

    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.validity:3600000}") long validityInMilliseconds) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(String username, Collection<String> authorities) {

        Date now = new Date();
        Date exp = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .subject(username)
                .claim("auth", authorities)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    private JwtParser parser() {
        return Jwts.parser()
                .setSigningKey(key)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            parser().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return parser()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getAuthorities(String token) {
        Object auth = parser()
                .parseClaimsJws(token)
                .getBody()
                .get("auth");

        if (auth instanceof List<?> list) {
            return list.stream().map(Object::toString).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
