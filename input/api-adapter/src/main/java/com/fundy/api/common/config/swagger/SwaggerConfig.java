package com.fundy.api.common.config.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(getInfo());
    }
    private Info getInfo() {
        return new Info().title("FUNDY API 명세서")
            .description("FUNDY 관련 API")
            .contact(new Contact()
                .name("김동원")
                .email("dongwon000103@gmail.com"))
            .version("v0.1");
    }
}
