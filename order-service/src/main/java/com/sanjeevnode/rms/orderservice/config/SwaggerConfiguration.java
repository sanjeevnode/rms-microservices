package com.sanjeevnode.rms.orderservice.config;

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
public class SwaggerConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:5002");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Sanjeev Singh");
        myContact.setEmail("me.sanjeevks@gmail.com");

        Info information = new Info()
                .title("Order Service API")
                .version("1.0")
                .description("API for managing Orders in the Restaurant Management System")
                .contact(myContact);

        // Define the security scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // Add the security requirement to use it globally
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        return new OpenAPI()
                .info(information)
                .servers(List.of(server))
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}