package com.supidan.aiinterview.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 定义安全方案名称，建议统一用 "BearerAuth" 作为 Key
        String securitySchemeName = "BearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("AI面试平台接口文档")
                        .description("AI模拟面试与能力提升平台")
                        .version("1.0.0")
                )
                // 1. 全局声明需要该安全认证
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        // 2. 定义具体的安全方案
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)      // 这里的 name 对应上面的 key
                                        .type(SecurityScheme.Type.HTTP) // 类型是 HTTP
                                        .scheme("bearer")              // 方案是 bearer
                                        .bearerFormat("JWT")           // 格式是 JWT
                                        .in(SecurityScheme.In.HEADER)  // 【关键】明确指定在 Header 中
                        ));
    }
}