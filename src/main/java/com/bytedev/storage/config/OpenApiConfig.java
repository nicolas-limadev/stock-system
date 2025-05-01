package com.bytedev.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
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
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://opensource.org/license/apache-2-0")));
    }
}

