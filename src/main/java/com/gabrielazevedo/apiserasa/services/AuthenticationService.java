package com.gabrielazevedo.apiserasa.services;

import com.gabrielazevedo.apiserasa.dtos.AuthenticationDTO;
import com.gabrielazevedo.apiserasa.dtos.LoginResponseDTO;
import com.gabrielazevedo.apiserasa.dtos.RegisterDTO;
import com.gabrielazevedo.apiserasa.dtos.ResponseDefaultDTO;
import com.gabrielazevedo.apiserasa.exceptions.UsernameAlreadyExistsException;
import com.gabrielazevedo.apiserasa.models.UserModel;
import com.gabrielazevedo.apiserasa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private static final String USER_CREATED = "User created successfully!";

    @Value("${api.response.success}")
    private String response_success;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO login(AuthenticationDTO authenticationDTO) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.gerenerateToken((UserModel) auth.getPrincipal());

        return new LoginResponseDTO(response_success, token, tokenService.getExpirationData(token));
    }

    public ResponseDefaultDTO register(RegisterDTO registerDTO) {
        if (userRepository.findByLogin(registerDTO.login()) != null) {
            throw new UsernameAlreadyExistsException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserModel newUser = new UserModel(registerDTO.login(), encryptedPassword, registerDTO.role());

        userRepository.save(newUser);

        return new ResponseDefaultDTO(response_success, USER_CREATED);
    }
}
