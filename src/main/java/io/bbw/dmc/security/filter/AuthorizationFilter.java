package io.bbw.dmc.security.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.bbw.dmc.constant.SecurityConstants;
import io.bbw.dmc.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (Arrays.asList(SecurityConstants.REGISTRATION_ROUTE,
                SecurityConstants.AUTHENTICATION_ROUTE).contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token;
        if (request.getRequestURI().equals("/message")) {
            token = request.getParameter("token");
        } else {
            token = new String(request.getHeader(SecurityConstants.AUTHORIZATION).split(" ")[1]);
        }
        var userId = JwtUtils.verifyToken(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }
}
