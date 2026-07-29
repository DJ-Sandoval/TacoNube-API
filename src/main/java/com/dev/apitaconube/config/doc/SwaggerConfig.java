package com.dev.apitaconube.config.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "API TACONUBE",
                description = "API for managing an after-sales system for taco shops",
                termsOfService = "www.unprogramadornace.com/terminos_y_condiciones",
                version = "1.0.0",
                contact = @Contact(
                        name = "Armando Sandoval",
                        url = "https://unprogramadornace.com",
                        email = "sandovalsantanajosearmando@mail.com"
                ),
                license = @License(
                        name = "Standard Software Use License for DevSandoval",
                        url = "www.unprogramadornace.com/licence"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8083"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "http://unprogramadornace:8080"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        name = "Security Token",
        description = "Access Token For My API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {}
