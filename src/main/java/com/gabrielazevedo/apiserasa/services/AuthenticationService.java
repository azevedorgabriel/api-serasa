package com.gabrielazevedo.apiserasa.services;

import com.gabrielazevedo.apiserasa.dtos.AuthenticationDTO;
import com.gabrielazevedo.apiserasa.dtos.LoginResponseDTO;
import com.gabrielazevedo.apiserasa.dtos.RegisterDTO;
import com.gabrielazevedo.apiserasa.dtos.RegisterResponseDTO;
import com.gabrielazevedo.apiserasa.models.UserModel;
import com.gabrielazevedo.apiserasa.repositories.ScoreRepository;
import com.gabrielazevedo.apiserasa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private static final String USER_CREATED = "User created successfully!";
    private static final String USER_EXISTS = "User already exists!";
    private static final String USER_INVALID = "Username or/and password are invalid!";

    @Value("${api.response.error}")
    private String response_error;
    @Value("${api.response.success}")
    private String response_success;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    public ResponseEntity<LoginResponseDTO> login(AuthenticationDTO authenticationDTO) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        try {
            var auth = this.authenticationManager.authenticate(userNamePassword);
            var token = tokenService.gerenerateToken((UserModel) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(response_success, "", token, tokenService.getExpirationData(token)));
        } catch (AuthenticationException authEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginResponseDTO(response_error, USER_INVALID, "", ""));
        }

    }

    public ResponseEntity<RegisterResponseDTO> register(RegisterDTO registerDTO) {
        if (this.userRepository.findByLogin(registerDTO.login()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponseDTO(response_error, USER_EXISTS));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserModel newUser = new UserModel(registerDTO.login(), encryptedPassword, registerDTO.role());

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponseDTO(HttpStatus.CREATED.name(), USER_CREATED));
    }
}
