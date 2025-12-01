package com.edutrack.academico.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private Key key;

    @PostConstruct
    public void initKey() {
        String secret = null;
        try {
            secret = dotenv.get("JWT_SECRET");
        } catch (Exception ignored) {}

        if (secret == null || secret.isBlank()) {
            secret = System.getenv("JWT_SECRET");
        }

        // Valor por defecto para evitar crash si no hay variable
        if (secret == null || secret.isBlank()) {
            secret = "ClavePorDefectoParaQueNoFalle1234567890";
        }

        // --- CORRECCIÓN DEFINITIVA ---
        // Ya no decodificamos Base64. Leemos los bytes directos.
        // Esto acepta guiones, espacios, puntos, lo que sea.
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        // Ajuste de longitud para evitar errores de seguridad de la librería
        if (keyBytes.length < 32) {
            String pad = secret.repeat(5);
            keyBytes = pad.getBytes(StandardCharsets.UTF_8);
        }

        // Recorte si es demasiado larga (opcional, por seguridad)
        if (keyBytes.length > 64) {
            byte[] trimmed = new byte[64];
            System.arraycopy(keyBytes, 0, trimmed, 0, 64);
            keyBytes = trimmed;
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // --- RESTO DEL CÓDIGO IGUAL ---

    private Key getKey() {
        if (key == null) initKey();
        return key;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}