package com.dev.apitaconube.service.impl;

import com.dev.apitaconube.domain.entity.Negocio;
import com.dev.apitaconube.domain.entity.Rol;
import com.dev.apitaconube.domain.entity.Usuario;
import com.dev.apitaconube.dto.request.RegistroNegocioRequest;
import com.dev.apitaconube.dto.response.NegocioResponse;
import com.dev.apitaconube.dto.response.RegistroNegocioResponse;
import com.dev.apitaconube.dto.response.UsuarioResponse;
import com.dev.apitaconube.exception.ArchivoInvalidoException;
import com.dev.apitaconube.exception.EmailYaRegistradoException;
import com.dev.apitaconube.exception.NegocioNoEncontradoException;
import com.dev.apitaconube.exception.RolNoEncontradoException;
import com.dev.apitaconube.repository.NegocioRepository;
import com.dev.apitaconube.repository.RolRepository;
import com.dev.apitaconube.repository.UsuarioRepository;
import com.dev.apitaconube.service.interfaces.NegocioService;
import com.dev.apitaconube.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class NegocioServiceImpl implements NegocioService {

    private static final String ROL_ADMIN = "ADMIN";
    private static final Set<String> TIPOS_IMAGEN_PERMITIDOS = Set.of("image/png", "image/jpeg", "image/webp");

    private final NegocioRepository negocioRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

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

    @Override
    @Transactional
    public NegocioResponse actualizarIcono(Long negocioId, MultipartFile archivo) {
        validarImagen(archivo);

        Negocio negocio = negocioRepository.findById(negocioId)
                .orElseThrow(() -> new NegocioNoEncontradoException(negocioId));

        // Si ya tenia icono, se borra el archivo viejo del storage antes de
        // guardar el nuevo, para no dejar archivos huerfanos acumulandose.
        if (negocio.getIconoUrl() != null) {
            storageService.eliminar(negocio.getIconoUrl());
        }

        String urlIcono = storageService.guardar(archivo, "negocios/" + negocioId);
        negocio.setIconoUrl(urlIcono);
        negocioRepository.save(negocio);

        return toNegocioResponse(negocio);
    }

    private void validarImagen(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new ArchivoInvalidoException("Debes seleccionar un archivo de imagen");
        }
        if (!TIPOS_IMAGEN_PERMITIDOS.contains(archivo.getContentType())) {
            throw new ArchivoInvalidoException("Formato no soportado. Usa PNG, JPG o WEBP");
        }
    }

    private NegocioResponse toNegocioResponse(Negocio negocio) {
        return new NegocioResponse(
                negocio.getId(),
                negocio.getNombre(),
                negocio.getEmail(),
                negocio.getEstado(),
                negocio.getIconoUrl()
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
