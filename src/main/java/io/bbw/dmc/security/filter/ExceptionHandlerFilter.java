package io.bbw.dmc.security.filter;

import java.io.IOException;

import io.formulate.web.common.error.AppError;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.bbw.dmc.constant.SecurityConstants;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private void enhanceResponse(Exception exception, HttpServletResponse response)
            throws IOException {
        response.setHeader(SecurityConstants.CONTENT_TYPE, SecurityConstants.JSON_CONTENT);

        int sc;
        AppError error;

        switch (exception.getClass().getSimpleName()) {
            // See what exception is thrown when jwt token expires
            case "BadCredentialsException":
                sc = HttpServletResponse.SC_UNAUTHORIZED;
                error = new AppError(exception.getMessage());
                break;
            default:
                sc = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                error = new AppError("Something went wrong!");
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
