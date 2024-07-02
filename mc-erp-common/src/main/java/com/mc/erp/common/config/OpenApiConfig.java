package com.mc.erp.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author: mrhuangzh
 * @date: 2024/6/7 0:22
 **/
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        // 访问路径：http://localhost:8080/swagger-ui/index.html
        return new OpenAPI().info(new Info()
                        .title("mc-erp API")
                        .description("mc-erp")
                        .version("0.0.1-SNAPSHOT"))
                .servers(Arrays.asList(new Server().url("http://localhost:8080").description("Local server"), new Server().url("https://api.example.com").description("Production server")));
    }
}
