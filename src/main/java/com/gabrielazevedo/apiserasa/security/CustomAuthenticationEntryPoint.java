package com.gabrielazevedo.apiserasa.security;

import com.gabrielazevedo.apiserasa.exceptions.InvalidTokenJwtException;
import com.gabrielazevedo.apiserasa.exceptions.RestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private RestExceptionHandler restExceptionHandler;
    @Autowired
    private SecurityFilter securityFilter;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //if token is missing, response with an invalid token error will be returned
        if (isTokenMissing(request)) {
            throw new InvalidTokenJwtException();
        }
        //if the user does not have the correct role for the action, response with an invalid user role will be returned
        restExceptionHandler.accessDeniedHandler(response);
    }

    private boolean isTokenMissing(HttpServletRequest request) {
        String token = securityFilter.recoveryToken(request);
        return token == null || token.isEmpty();
    }
}
