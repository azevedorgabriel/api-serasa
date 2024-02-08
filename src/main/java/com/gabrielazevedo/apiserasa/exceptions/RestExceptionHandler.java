package com.gabrielazevedo.apiserasa.exceptions;

import com.gabrielazevedo.apiserasa.dtos.ResponseDefaultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Value("${api.response.error}")
    private String response_error;

    private static final String INVALID_USER = "User and/or password are invalid!";

    @ExceptionHandler({InternalAuthenticationServiceException.class, AuthenticationException.class})
    private ResponseEntity<ResponseDefaultDTO> invalidUserPassHandler(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDefaultDTO(response_error, INVALID_USER));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    private ResponseEntity<ResponseDefaultDTO> usernameAlreadyExistsHandler(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDefaultDTO(response_error, ex.getMessage()));
    }

    @ExceptionHandler(PersonNotFoundException.class)
    private ResponseEntity<ResponseDefaultDTO> personNotFoundHandler(PersonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDefaultDTO(response_error, ex.getMessage()));
    }

    // Método utilizado em Security Filter devido Spring nao direcionar exception para este ControllerAdvice
    public ResponseDefaultDTO invalidTokenJwtHandler() {
        return new ResponseDefaultDTO(response_error, new InvalidTokenJwtException().getMessage());
    }
}
