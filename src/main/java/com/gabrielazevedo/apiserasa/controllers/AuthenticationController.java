package com.gabrielazevedo.apiserasa.controllers;

import com.gabrielazevedo.apiserasa.dtos.AuthenticationDTO;
import com.gabrielazevedo.apiserasa.dtos.LoginResponseDTO;
import com.gabrielazevedo.apiserasa.dtos.RegisterDTO;
import com.gabrielazevedo.apiserasa.models.UserModel;
import com.gabrielazevedo.apiserasa.repositories.UserRepository;
import com.gabrielazevedo.apiserasa.services.AuthenticationService;
import com.gabrielazevedo.apiserasa.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        return authService.login(authenticationDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
}
