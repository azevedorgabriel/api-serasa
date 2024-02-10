package com.gabrielazevedo.apiserasa.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.gabrielazevedo.apiserasa.exceptions.InvalidTokenJwtException;
import com.gabrielazevedo.apiserasa.exceptions.RestExceptionHandler;
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
    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = this.recoveryToken(request);

            if (!token.isEmpty()) {
                UserDetails userDetails = userRepository.findByLogin(tokenService.validateToken(token));

                if (userDetails == null) {
                    throw new InvalidTokenJwtException();
                }

                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | InvalidTokenJwtException ex) {
            restExceptionHandler.invalidTokenJwtHandler(response);
        }
    }

    public String recoveryToken(HttpServletRequest request) {
        var authHeaderAuth = request.getHeader("Authorization");
        if (authHeaderAuth == null) {
            return "";
        }

        return authHeaderAuth.replace("Bearer ", "");
    }
}
