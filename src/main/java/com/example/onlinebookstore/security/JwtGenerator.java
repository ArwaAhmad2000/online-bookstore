package com.example.onlinebookstore.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private final Environment env;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + Long.parseLong(env.getProperty("JWT_EXPIRATION")));
        Key key = Keys.hmacShaKeyFor(env.getProperty("SECRET_KEY").getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder().setSubject(email).setIssuedAt(currentDate).setExpiration(expireDate).signWith(key)
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            String secret_key = env.getProperty("SECRET_KEY");
            Key key = Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        String secret_key = env.getProperty("SECRET_KEY");
        Key key = Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

}
