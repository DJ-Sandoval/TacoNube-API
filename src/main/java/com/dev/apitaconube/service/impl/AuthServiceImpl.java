package com.dev.apitaconube.service.impl;

import com.dev.apitaconube.config.security.JwtService;
import com.dev.apitaconube.domain.entity.Token;
import com.dev.apitaconube.domain.entity.Usuario;
import com.dev.apitaconube.dto.request.LoginRequest;
import com.dev.apitaconube.dto.response.LoginResponse;
import com.dev.apitaconube.exception.CredencialesInvalidasException;
import com.dev.apitaconube.repository.TokenRepository;
import com.dev.apitaconube.repository.UsuarioRepository;
import com.dev.apitaconube.service.interfaces.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .filter(Usuario::getActivo)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            throw new CredencialesInvalidasException();
        }

        String jwt = jwtService.generarToken(usuario);
        guardarToken(usuario, jwt);

        return new LoginResponse(
                jwt,
                "Bearer",
                usuario.getId(),
                usuario.getNegocio().getId(),
                usuario.getNombre(),              // nombreUsuario
                usuario.getNegocio().getNombre(), // negocioNombre
                usuario.getRol().getNombre()      // rol
        );
    }

    private void guardarToken(Usuario usuario, String jwt) {
        // Reutilizamos los claims del token recien generado (issuedAt/expiration)
        // en vez de recalcular la expiracion por separado, para que la fila
        // en BD sea siempre un reflejo exacto de lo que dice el JWT.
        Claims claims = jwtService.extraerClaims(jwt);

        Token token = Token.builder()
                .usuario(usuario)
                .negocio(usuario.getNegocio())
                .nombreUsuario(usuario.getNombre())
                .nombreNegocio(usuario.getNegocio().getNombre())
                .rol(usuario.getRol().getNombre())
                .token(jwt)
                .fechaEmision(toLocalDateTime(claims.getIssuedAt()))
                .fechaExpiracion(toLocalDateTime(claims.getExpiration()))
                .build();

        tokenRepository.save(token);
    }

    private LocalDateTime toLocalDateTime(java.util.Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}

