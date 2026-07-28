package com.dev.apitaconube.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Se ejecuta una vez por request. Si viene un Bearer token valido, arma el
 * Authentication a partir de los claims (sin ir a base de datos) y lo deja
 * en el SecurityContext. Si no viene token, o es invalido, simplemente no
 * autentica y deja que el resto de la cadena decida (rutas publicas pasan,
 * rutas protegidas terminan en 401 via JwtAuthenticationEntryPoint).
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTORIZACION = "Authorization";
    private static final String PREFIJO_BEARER = "Bearer ";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extraerToken(request);

        if (token != null && jwtService.esValido(token)) {
            AuthenticatedUser authenticatedUser = jwtService.toAuthenticatedUser(token);

            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + authenticatedUser.rol()));

            var authentication = new UsernamePasswordAuthenticationToken(
                    authenticatedUser, null, authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extraerToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_AUTORIZACION);
        if (header != null && header.startsWith(PREFIJO_BEARER)) {
            return header.substring(PREFIJO_BEARER.length());
        }
        return null;
    }
}