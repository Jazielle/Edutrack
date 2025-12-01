package com.edutrack.academico.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.nio.charset.StandardCharsets; // Importación necesaria
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // (Tu manejo de dotenv sigue igual)
    private final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private Key key;

    @PostConstruct
    public void initKey() {
        String secret = null;

        try {
            secret = dotenv.get("JWT_SECRET");
        } catch (Exception ignored) {
        }

        if (secret == null || secret.isBlank()) {
            secret = System.getenv("JWT_SECRET");
        }

        if (secret == null || secret.isBlank()) {
            // Error de despliegue si la variable no existe
            throw new IllegalStateException("JWT_SECRET no se encontró en las variables de entorno.");
        }

        // -----------------------------------------------------------
        // SOLUCIÓN: Usamos la clave como una cadena normal y
        // dejamos que la librería la convierta a bytes seguros (Base64)
        // -----------------------------------------------------------

        // 1. Verificación de longitud mínima (opcional, pero buena práctica)
        if (secret.length() < 32) {
            throw new IllegalStateException("JWT_SECRET debe tener al menos 32 caracteres.");
        }

        // 2. Generar la clave de forma segura desde la cadena (SIN DECODIFICACIÓN BASE64 MANUAL)
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // -----------------------------------------------------------
    // EL RESTO DEL CÓDIGO PERMANECE IGUAL
    // -----------------------------------------------------------

    private Key getKey() {
        if (key == null) {
            initKey();
        }
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
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
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
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }
}