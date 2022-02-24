package com.karold.swreportapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.service.CustomHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CustomHttpClient customHttpClient() {
        return new CustomHttpClient("http://localhost:8080/api");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
