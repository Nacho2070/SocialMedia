package com.PostService.PostService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@EnableWebSecurity
public class ConfigurationWeb {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**","/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html","/webjars/**","/swagger/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public WebClient webClient() {
        return  WebClient.builder()
                .baseUrl("http://localhost:8181/")
                .filter(new ServletBearerExchangeFilterFunction())
                .build();
    }

}
