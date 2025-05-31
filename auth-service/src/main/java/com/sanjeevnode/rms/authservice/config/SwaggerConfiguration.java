package com.sanjeevnode.rms.authservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:5000");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Sanjeev Singh");
        myContact.setEmail("me.sanjeevks@gmail.com");

        Info information = new Info()
                .title("Authentication Service API")
                .version("1.0")
                .description("API for managing user authentication and authorization")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}