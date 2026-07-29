package com.dev.apitaconube.service.interfaces;

import com.dev.apitaconube.dto.request.RegistroNegocioRequest;
import com.dev.apitaconube.dto.response.NegocioResponse;
import com.dev.apitaconube.dto.response.RegistroNegocioResponse;
import org.springframework.web.multipart.MultipartFile;

public interface NegocioService {

    /**
     * Crea un Negocio junto con su primer usuario (rol ADMIN) en una sola
     * operacion transaccional. Un negocio sin usuario admin no tiene forma
     * de operarse, por eso ambos se crean juntos y no en pasos separados.
     */
    RegistroNegocioResponse registrarNegocio(RegistroNegocioRequest request);

    /**
     * Sube (o reemplaza) el icono del negocio. Si ya tenia uno, el anterior
     * se borra del storage para no dejar archivos huerfanos.
     */
    NegocioResponse actualizarIcono(Long negocioId, MultipartFile archivo);
}
