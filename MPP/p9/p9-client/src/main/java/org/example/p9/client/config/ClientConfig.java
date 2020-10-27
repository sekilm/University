package org.example.p9.client.config;

import org.example.p9.client.ui.Console;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.example.p9.web.converter.ClientConverter;
import org.example.p9.web.converter.MovieConverter;
import org.example.p9.web.converter.RentConverter;

@Configuration
public class ClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    Console console() {
        return new Console();
    }

    @Bean
    ClientConverter clientConverter() {
        return new ClientConverter();
    }

    @Bean
    MovieConverter movieConverter() {
        return new MovieConverter();
    }

    @Bean
    RentConverter rentConverter() {
        return new RentConverter();
    }
}
