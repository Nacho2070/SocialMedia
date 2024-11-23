package com.PostService.PostService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigurationWeb {

    @Bean
    public WebClient webClient() {

        return  WebClient.builder()
                .baseUrl("http://localhost:2")
                .build();
    }
}
