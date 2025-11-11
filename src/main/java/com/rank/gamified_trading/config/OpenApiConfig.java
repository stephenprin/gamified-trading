package com.rank.gamified_trading.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gamifiedTradingOpenAPI() {
      return new OpenAPI()
               .info(new Info()
                       .title("Gamified Trading System API")
                      .version("1.0.0")
                   .description("API documentation for the Gamified Trading System with Ranking and Gem Rewards.")
                       .contact(new Contact()
                               .name("Prince Nmezi")
                              .email("stephenprince427@gmail.com")
                             .url("https://github.com/stephenprin"))
                     .license(new License()
                              .name("MIT License")
                               .url("https://opensource.org/licenses/MIT")));
    }
}

