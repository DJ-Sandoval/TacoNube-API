package com.dev.apitaconube.config.security;

import com.dev.apitaconube.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    private static final String CLAIM_USUARIO_ID = "usuarioId";
    private static final String CLAIM_NEGOCIO_ID = "negocioId";
    private static final String CLAIM_USUARIO_NOMBRE = "nombreUsuario";
    private static final String CLAIM_Negocio_NOMBRE = "negocioNombre";
    private static final String CLAIM_ROL = "rol";

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(Usuario usuario) {
        Instant ahora = Instant.now();
        Instant expira = ahora.plusMillis(expirationMs);

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim(CLAIM_USUARIO_ID, usuario.getId())
                .claim(CLAIM_NEGOCIO_ID, usuario.getNegocio().getId())
                .claim(CLAIM_ROL, usuario.getRol().getNombre())
                .claim(CLAIM_USUARIO_NOMBRE, usuario.getNombre())
                .claim(CLAIM_Negocio_NOMBRE, usuario.getNegocio().getNombre()) // Corrección aquí
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(expira))
                .signWith(signingKey())
                .compact();
    }

    public Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean esValido(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extraerNombreNegocio(String token) {
        Claims claims = extraerClaims(token);
        return claims.get(CLAIM_Negocio_NOMBRE, String.class);
    }

    public AuthenticatedUser toAuthenticatedUser(String token) {
        Claims claims = extraerClaims(token);
        Long usuarioId = claims.get(CLAIM_USUARIO_ID, Long.class);
        Long negocioId = claims.get(CLAIM_NEGOCIO_ID, Long.class);
        String rol = claims.get(CLAIM_ROL, String.class);
        return new AuthenticatedUser(usuarioId, negocioId, rol);
    }
}

