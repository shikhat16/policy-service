package com.insurance.policy_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Insurance Policy Service API")
                        .version("1.0.0")
                        .description("REST API documentation for the Policy Service microservice. " +
                                "Includes CRUD operations, validation, pagination, and sorting.")
                        .contact(new Contact()
                                .name("Backend Team")
                                .email("backend-team@insurance.com")
                                .url("https://insurance.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}