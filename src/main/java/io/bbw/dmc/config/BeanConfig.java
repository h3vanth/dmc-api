package io.bbw.dmc.config;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import io.bbw.dmc.constant.SecurityConstants;

@Configuration
public class BeanConfig {
    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList(System.getenv("ALLOWED_ORIGINS")));
        corsConfiguration.setAllowedHeaders(Arrays.asList(SecurityConstants.ALLOWED_HEADERS));
        corsConfiguration.setAllowedMethods(Arrays.asList(SecurityConstants.ALLOWED_METHODS));
        corsConfiguration.setExposedHeaders(Arrays.asList(SecurityConstants.EXPOSED_HEADERS));
        return corsConfiguration;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        // TODO: Write a custom deserializer / see if we can configure the result
        return new ObjectMapper().registerModule(new JodaModule());
    }
}
