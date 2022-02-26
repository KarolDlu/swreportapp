package com.karold.swreportapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.service.CustomHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${swapi.baseUrl}")
    private String swapiBaseUrl;

    @Bean
    public CustomHttpClient customHttpClient() {
        return new CustomHttpClient(swapiBaseUrl);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
