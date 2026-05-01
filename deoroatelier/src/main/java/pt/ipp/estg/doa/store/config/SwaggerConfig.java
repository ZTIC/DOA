package pt.ipp.estg.doa.store.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Deoroatelier API")
                        .version("1.0")
                        .description("API para gestão da loja de joalharia")
                        .contact(new Contact()
                                .name("Valdemar Buco")
                                .email("valdemar.buco@gmail.com")
                                .url("https://github.com/LemonProust/doa/tree/main/deoroatelier/")
                        )
                        .termsOfService("You may use it however you want.")
                )
                .servers(Arrays.asList(
                                new Server()
                                        .description("Local ENV")
                                        .url("http://localhost:8011"),
                                new Server().description("PROD ENV")
                                        .url("https://www.notdefined:80")
                        )
                )
                .components(
                        new Components()
                                .addSecuritySchemes("bearer",
                                        new SecurityScheme()
                                                .name("bearerAuth")
                                                .description("JWT auth description")
                                                .scheme("bearer")
                                                .type(SecurityScheme.Type.HTTP)
                                                .bearerFormat("JWT")
                                ));


    }
}
