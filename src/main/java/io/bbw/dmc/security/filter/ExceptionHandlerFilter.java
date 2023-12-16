package io.bbw.dmc.security.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.bbw.dmc.constant.SecurityConstants;
import io.bbw.dmc.model.Error;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private void enhanceResponse(Exception exception, HttpServletResponse response)
            throws IOException {
        response.setHeader(SecurityConstants.CONTENT_TYPE, SecurityConstants.JSON_CONTENT);

        int sc;
        Error error;

        switch (exception.getClass().getSimpleName()) {
            case "BadCredentialsException":
                sc = HttpServletResponse.SC_BAD_REQUEST;
                error = new Error(exception.getMessage());
                break;
            case "EntityNotFoundException":
                sc = HttpServletResponse.SC_UNAUTHORIZED;
                error = new Error(exception.getMessage());
                break;
            case "RuntimeException":
                sc = HttpServletResponse.SC_UNAUTHORIZED;
                error = new Error("Invalid authorization header");
                break;
            default:
                sc = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                error = new Error("Something went wrong!");
        }

        response.setStatus(sc);
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        response.getWriter().flush();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            enhanceResponse(exception, response);
        }
    }

}
