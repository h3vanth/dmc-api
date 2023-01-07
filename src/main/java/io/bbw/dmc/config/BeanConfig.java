package io.bbw.dmc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import io.bbw.dmc.constant.SecurityConstants;
import lombok.Getter;
import lombok.Setter;

@Configuration
public class BeanConfig {

    @Getter
    @Setter
    @Value("${cors.allowed-origins}")
    private String ALLOWED_ORIGINS;

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGINS));
        corsConfiguration.setAllowedHeaders(Arrays.asList(SecurityConstants.ALLOWED_HEADERS));
        corsConfiguration.setAllowedMethods(Arrays.asList(SecurityConstants.ALLOWED_METHODS));
        corsConfiguration.setExposedHeaders(Arrays.asList(SecurityConstants.EXPOSED_HEADERS));
        return corsConfiguration;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
