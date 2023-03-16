package io.bbw.dmc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.bbw.dmc.constant.SecurityConstants;
import io.bbw.dmc.security.filter.AuthenticationFilter;
import io.bbw.dmc.security.filter.AuthorizationFilter;
import io.bbw.dmc.security.filter.ExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final CorsConfiguration corsConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().configurationSource(request -> corsConfiguration).and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTRATION_ROUTE)
                .permitAll()
                .requestMatchers("/images/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager, objectMapper))
                .addFilterAfter(new AuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
