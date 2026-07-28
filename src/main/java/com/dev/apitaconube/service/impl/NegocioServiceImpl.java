package com.dev.apitaconube.service.impl;

import com.dev.apitaconube.domain.entity.Negocio;
import com.dev.apitaconube.domain.entity.Rol;
import com.dev.apitaconube.domain.entity.Usuario;
import com.dev.apitaconube.dto.request.RegistroNegocioRequest;
import com.dev.apitaconube.dto.response.NegocioResponse;
import com.dev.apitaconube.dto.response.RegistroNegocioResponse;
import com.dev.apitaconube.dto.response.UsuarioResponse;
import com.dev.apitaconube.exception.EmailYaRegistradoException;
import com.dev.apitaconube.exception.RolNoEncontradoException;
import com.dev.apitaconube.repository.NegocioRepository;
import com.dev.apitaconube.repository.RolRepository;
import com.dev.apitaconube.repository.UsuarioRepository;
import com.dev.apitaconube.service.interfaces.NegocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NegocioServiceImpl implements NegocioService {

    private static final String ROL_ADMIN = "ADMIN";

    private final NegocioRepository negocioRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegistroNegocioResponse registrarNegocio(RegistroNegocioRequest request) {

        String emailAdmin = request.admin().email();
        if (usuarioRepository.existsByEmail(emailAdmin)) {
            throw new EmailYaRegistradoException(emailAdmin);
        }

        Rol rolAdmin = rolRepository.findByNombre(ROL_ADMIN)
                .orElseThrow(() -> new RolNoEncontradoException(ROL_ADMIN));

        Negocio negocio = Negocio.builder()
                .nombre(request.negocio().nombre())
                .rfc(request.negocio().rfc())
                .direccion(request.negocio().direccion())
                .telefono(request.negocio().telefono())
                .email(request.negocio().email())
                .build();
        negocio = negocioRepository.save(negocio);

        Usuario admin = Usuario.builder()
                .negocio(negocio)
                .rol(rolAdmin)
                .nombre(request.admin().nombre())
                .email(request.admin().email())
                .passwordHash(passwordEncoder.encode(request.admin().password()))
                .activo(true)
                .build();
        admin = usuarioRepository.save(admin);

        return new RegistroNegocioResponse(
                toNegocioResponse(negocio),
                toUsuarioResponse(admin)
        );
    }

    private NegocioResponse toNegocioResponse(Negocio negocio) {
        return new NegocioResponse(
                negocio.getId(),
                negocio.getNombre(),
                negocio.getEmail(),
                negocio.getEstado()
        );
    }

    private UsuarioResponse toUsuarioResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }
}
