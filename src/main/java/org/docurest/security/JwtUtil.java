package org.docurest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate secret key
    private static final long VALIDITY = 3600000; // 1 hour

    public String generateToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + VALIDITY))
                .signWith(secretKey) // Updated to use SecretKey
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // Updated to use parserBuilder
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractSubject(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserAuthDetails userAuthDetails) {
        final String subject = extractSubject(token);
        return (subject.equals(userAuthDetails.getId()) && !isTokenExpired(token));
    }
}
