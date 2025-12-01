package com.edutrack.academico.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.nio.charset.StandardCharsets; // Importación necesaria para leer texto normal
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

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

        // Si no encuentra la clave, usamos una por defecto para que NO falle al arrancar
        if (secret == null || secret.isBlank()) {
            System.out.println("ADVERTENCIA: No se encontró JWT_SECRET. Usando clave por defecto insegura.");
            secret = "EstaEsUnaClavePorDefectoParaQueNoFalleElDespliegue12345";
        }

        // --- CAMBIO SOLICITADO: ELIMINADA LA OBLIGACIÓN DE BASE64 ---

        // Antes (Lo que causaba el error):
        // byte[] keyBytes = Decoders.BASE64.decode(secret.trim());

        // Ahora (Acepta cualquier texto):
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        // Eliminamos la validación estricta de longitud que lanzaba excepciones
        // Solo nos aseguramos de que Keys.hmacShaKeyFor no falle (necesita algo de longitud)
        if (keyBytes.length < 32) {
            // Si la clave es muy corta, la repetimos para que alcance el largo necesario sin error
            String secretPad = secret.repeat(32);
            keyBytes = secretPad.getBytes(StandardCharsets.UTF_8);
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

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