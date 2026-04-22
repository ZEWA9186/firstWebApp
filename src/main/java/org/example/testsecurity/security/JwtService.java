package org.example.testsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.config.JwtConfig;
import org.example.testsecurity.jpa.Profile;
import org.example.testsecurity.jpa.Role;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public String generateJwtToken(Profile profile) {
        return Jwts.builder()
                .claim("roles", profile.getRoles().stream()
                        .map(Role::getName)
                        .toList()
                )
                .claim("id", profile.getId())
                .subject(profile.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractEmail(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecretKey()));
    }
}
