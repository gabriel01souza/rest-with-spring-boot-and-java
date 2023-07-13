package br.com.erudio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API weith Java 19 and Spring Boot 3.0.1")
                        .version("v1")
                        .description("Customized API from Gabriel Cardoso")
                        .termsOfService("http://pub.erudio.com.br/meus-cursos")
                        .license(new License()
                                .name("Apache 2")
                                .url("http://pub.erudio.com.br/meus-cursos")
                        )
                );
    }
}
