package com.company.attendance.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        //  Allow Swagger + Auth APIs
        if (path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.startsWith("/api/auth")) {

            chain.doFilter(request, response);
            return;
        }

        try {
            String header = req.getHeader("Authorization");

            //  If no token → just continue (DON'T throw error)
            if (header == null || !header.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            String token = header.substring(7);

            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            //  Store in request
            req.setAttribute("userEmail", email);
            req.setAttribute("userRole", role);

        } catch (Exception e) {
            // VERY IMPORTANT → never crash Swagger
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}