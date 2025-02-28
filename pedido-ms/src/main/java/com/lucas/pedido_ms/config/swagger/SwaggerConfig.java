package com.lucas.pedido_ms.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private String url = "http://localhost:8080/";

    @Bean
    public OpenAPI openApi(){

        Contact contact = new Contact();

        contact.setEmail("furquimmsw@gmail.com");
        contact.setName("furqas");
        contact.setUrl("https://github.com/l-furquim");

        Info info = new Info()
                .title("Ifood clone order microservice")
                .version("1.0")
                .description("Microservice for the orders")
                .contact(contact);

        Server server = new Server();
        server.setUrl(url);

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server))
                .components(new Components().addSecuritySchemes("Token de autorização", securityScheme))
                .addSecurityItem(new SecurityRequirement().addList("Token de autorização"));
    }
}
