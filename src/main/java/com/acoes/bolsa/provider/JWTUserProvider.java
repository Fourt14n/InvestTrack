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


    public String validateToken(String token) {
        try {
            token = token.replace("Bearer", "").trim();

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return jwt.getSubject();

        } catch (JWTVerificationException e) {
            System.err.println("Token verification failed: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error during token validation: " + e.getMessage());
            return null;
        }
    }
}