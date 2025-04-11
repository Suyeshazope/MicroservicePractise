package com.microservices.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

    private final String[] freeResourcesUrls = {"/swagger-ui.html" , "/swagger-ui/**" , "/v3/api-docs/**" , "/swagger-resources/**" ,
    "/api-docs/**" , "/aggregate/**" , "/actuator/prometheus"} ;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.
                configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("*");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addAllowedMethod("*");
                    return corsConfiguration;
                }));

        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(freeResourcesUrls).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 ->oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
