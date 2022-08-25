package com.learning.users.config;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FackerBeanConfig {
    @Bean
    public Faker getFaker() {
        return new Faker();
    }
}
