package com.gabrielazevedo.apiserasa.controllers;

import com.gabrielazevedo.apiserasa.dtos.AuthenticationDTO;
import com.gabrielazevedo.apiserasa.dtos.LoginResponseDTO;
import com.gabrielazevedo.apiserasa.dtos.RegisterDTO;
import com.gabrielazevedo.apiserasa.dtos.ResponseDefaultDTO;
import com.gabrielazevedo.apiserasa.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth", produces = {"application/json"})
@Tag(name = "api-serasa/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza o Login", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successfully!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User and/or password is invalid!")
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(authenticationDTO));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza o Cadastro de Usuario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully!"),
            @ApiResponse(responseCode = "400", description = "User already exists!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User must be ADMIN"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User or/and password are invalid!")
    })
    public ResponseEntity<ResponseDefaultDTO> register(@RequestBody @Valid RegisterDTO registerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerDTO));
    }
}
