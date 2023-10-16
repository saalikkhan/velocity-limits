package com.vault.velocity.limits.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Vault",
                        email = "vault@vault.com",
                        url = "TBD"),
                description = "Open-API Swagger documentation for Velocity limit services.",
                title = "Open-API documentation for funds-service - Provided by Vault",
                version = "1.0.0",
                license = @License(
                        url = "TBD",
                        name = "TBD"
                ),
                termsOfService = "TBD"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/funds-service"
                ),
                @Server(
                        description = "Dev ENV",
                        url = "TBD"
                ),
                @Server(
                        description = "Stage ENV",
                        url = "TBD"
                ),
                @Server(
                        description = "Production ENV",
                        url = "TBD"
                )
        }
)
public class OpenApiConfig {
}
