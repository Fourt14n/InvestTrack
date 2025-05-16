package com.acoes.bolsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean 
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("API sobre ações da bolsa de valores")
						.description("API Rest do sistema da InvestTrack")
						.contact(new Contact()
								.email("hebertt.lopes14@gmail.com")
								.name("Hebert Lopes"))
						.version("1.0.0")
						);
	}
}
