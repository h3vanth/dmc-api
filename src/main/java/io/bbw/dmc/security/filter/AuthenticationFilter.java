package io.bbw.dmc.security.filter;

import java.io.IOException;
import java.util.Base64;

import io.formulate.web.common.error.AppError;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.bbw.dmc.constant.SecurityConstants;
import io.bbw.dmc.model.User;
import io.bbw.dmc.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.setFilterProcessesUrl(SecurityConstants.AUTHENTICATION_ROUTE);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        var credentials = new String(
                Base64.getDecoder().decode(request.getHeader(SecurityConstants.AUTHORIZATION).split(" ")[1]))
                .split(":");
        var email = credentials[0];
        var password = credentials[1];

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException {
        var user = (User) authResult.getPrincipal();
        String token = JwtUtils.generateToken(user);
        response.addHeader(SecurityConstants.AUTHORIZATION,
                new StringBuilder().append(SecurityConstants.BEARER).append(token).toString());
        response.addHeader(SecurityConstants.CONTENT_TYPE, SecurityConstants.JSON_CONTENT);
        String jsonString = new StringBuilder().append("{ \"email\": \"").append(user.getEmail()).append("\", \"passcode\": \"").append(user.getPasscode()).append("\", \"userId\": \"").append(user.getUserId()).append( "\" }").toString();
        response.getWriter().write(jsonString);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(new AppError(failed.getMessage())));
        response.getWriter().flush();
    }
}
