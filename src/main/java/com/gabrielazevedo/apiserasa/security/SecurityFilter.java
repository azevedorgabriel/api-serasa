package com.gabrielazevedo.apiserasa.security;

import com.gabrielazevedo.apiserasa.repositories.UserRepository;
import com.gabrielazevedo.apiserasa.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoveryToken(request);

        if (!token.isEmpty()) {
            var login = tokenService.validateToken(token);

            UserDetails userDetails = userRepository.findByLogin(login);

            var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    public String recoveryToken(HttpServletRequest request) {
        var authHeaderAuth = request.getHeader("Authorization");
        if (authHeaderAuth == null) {
            return "";
        }

        return authHeaderAuth.replace("Bearer ", "");
    }
}
