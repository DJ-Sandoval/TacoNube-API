package com.dev.apitaconube.domain.entity;

import com.dev.apitaconube.domain.enums.EstadoNegocio;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "negocios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nombre;

    @Size(max = 20)
    private String rfc;

    @Size(max = 255)
    private String direccion;

    @Size(max = 20)
    private String telefono;

    @Email
    @Size(max = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoNegocio estado = EstadoNegocio.ACTIVO;

    // URL publica del icono/logo (hoy en disco local, mas adelante en
    // DigitalOcean Spaces). Null mientras el negocio no haya subido ninguno.
    @Size(max = 255)
    @Column(name = "icono_url", length = 255)
    private String iconoUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
