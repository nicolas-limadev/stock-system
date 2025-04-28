package com.bytedev.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Storage Management API")
                        .version("1.0")
                        .description("API para gerenciamento de produtos e estoques")
                        .contact(new Contact()
                                .name("Byte Dev")
                                .email("contato@bytedev.com.br")
                                .url("https://bytedev.com.br")) // HTTPS aqui
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))); // HTTPS aqui também
    }
}

