package com.gabrielazevedo.apiserasa.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gabrielazevedo.apiserasa.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private static final int expirationAfterHours = 1;

    @Value("${api.serasa.issuer}")
    private String issuer;

    @Value("${api.serasa.timezone}")
    private String brasiliaTimeZone;

    @Value("${api.security.token.secret}")
    private String secret;
    public String gerenerateToken(UserModel userModel) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userModel.getLogin())
                .withExpiresAt(generateExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(expirationAfterHours).toInstant(ZoneOffset.of(brasiliaTimeZone));
    }

    public String getExpirationData(String token) {
        return JWT.decode(token).getExpiresAt().toString();
    }
}
