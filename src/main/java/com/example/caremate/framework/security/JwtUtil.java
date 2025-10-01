package com.example.caremate.framework.security;

import com.example.caremate.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;

    private static final String SECRET_KEY_BASE64 = "VXhqR3RCWUJiY01FUzhwVzZ5RDBsRVNIM0RYVE1iRkhMSmxsV1BMTDhEcz0=";
    private static final long EXPIRATION_TIME = 86400000L;

    public JwtUtil() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY_BASE64);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(Object user) {
        if (!(user instanceof User)) {
            throw new IllegalArgumentException("Invalid user type");
        }

        User u = (User) user;

        return Jwts.builder()
                .setSubject(u.getEmail())
                .claim("userId", u.getId())
                .claim("role", u.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String extractUserId(String token) {
        return extractClaims(token).get("userId", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
