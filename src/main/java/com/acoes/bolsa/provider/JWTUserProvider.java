package com.acoes.bolsa.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTUserProvider {
    @Value("${security.token.secret.user}")
    private String secretKey;

    public String validadeToken(String token) {
        token = token.replace("Barear", "");
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm).build().verify(token).getSubject();

        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return "";

        }
    }
}
