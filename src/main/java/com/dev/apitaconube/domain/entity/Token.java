package com.dev.apitaconube.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Registro historico de cada JWT emitido en login. nombreUsuario y
 * nombreNegocio son snapshot (no se actualizan si luego cambian) para poder
 * listar sesiones sin necesidad de join, y para conservar el dato aunque el
 * usuario o negocio cambien de nombre despues.
 */
@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nombre_usuario", nullable = false, length = 150)
    private String nombreUsuario;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nombre_negocio", nullable = false, length = 150)
    private String nombreNegocio;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String rol;

    @NotBlank
    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @NotNull
    @Column(name = "fecha_emision", nullable = false, updatable = false)
    private LocalDateTime fechaEmision;

    @NotNull
    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    // Pensado para cuando armemos logout/invalidacion manual de tokens.
    @Column(nullable = false)
    @Builder.Default
    private Boolean revocado = false;
}

