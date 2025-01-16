package com.jhcs.wavechat;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Classe principal da aplicação Wavechat.
 * Configura a aplicação Spring Boot e define o esquema de segurança OAuth2 com Keycloak.
 */
@EnableJpaAuditing
@SpringBootApplication
@SecurityScheme(
		name = "keycloak",
		type = SecuritySchemeType.OAUTH2,
		bearerFormat = "JWT",
		scheme = "bearer",
		in = SecuritySchemeIn.HEADER,
		flows = @OAuthFlows(
				password = @OAuthFlow(
						authorizationUrl = "http://localhost:9090/realms/wavechat/protocol/openid-connect/auth",
						tokenUrl = "http://localhost:9090/realms/wavechat/protocol/openid-connect/token"
				)
		)
)
public class WavechatApplication {

	/**
	 * Método principal que inicia a aplicação Spring Boot.
	 *
	 * @param args Argumentos de linha de comando.
	 */
	public static void main(String[] args) {
		SpringApplication.run(WavechatApplication.class, args);
	}

}