package io.bbw.dmc.security.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.bbw.dmc.constant.SecurityConstants;
import io.bbw.dmc.pojo.Error;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private void enhanceResponse(Exception exception, HttpServletResponse response)
            throws JsonProcessingException, IOException {
        response.setHeader(SecurityConstants.CONTENT_TYPE, SecurityConstants.JSON_CONTENT);

        int sc;
        Error error;

        switch (exception.getClass().getSimpleName()) {
            case "BadCredentialsException":
                sc = HttpServletResponse.SC_BAD_REQUEST;
                error = new Error(Arrays.asList(exception.getMessage()));
                break;
            case "EntityNotFoundException":
                sc = HttpServletResponse.SC_UNAUTHORIZED;
                error = new Error(Arrays.asList(exception.getMessage()));
                break;
            case "RuntimeException":
                sc = HttpServletResponse.SC_UNAUTHORIZED;
                error = new Error(Arrays.asList("Invalid authorization header"));
                break;
            default:
                sc = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                error = new Error(Arrays.asList("Something went wrong!"));
        }

        response.setStatus(sc);
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        response.getWriter().flush();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            enhanceResponse(exception, response);
        }
    }

}
