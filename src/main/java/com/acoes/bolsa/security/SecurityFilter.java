package com.acoes.bolsa.security;

import com.acoes.bolsa.provider.JWTUserProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUserProvider jwtUserProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            try {
                String token = authHeader.replace("Bearer ", "").trim();
                String userId = jwtUserProvider.validateToken(token);

                if (userId == null || userId.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                    return;
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Falha na autenticação");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}