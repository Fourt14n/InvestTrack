package com.acoes.bolsa.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTUserProvider {

    @Value("${security.token.secret.user}")
    private String secretKey;

    public DecodedJWT validadeToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        token = token.replace("Bearer ", "").trim(); // remove prefixo e espaços

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("java")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}

