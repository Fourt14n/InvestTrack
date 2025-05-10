package com.acoes.bolsa.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTUserProvider {
    @Value("${security.token.secret.user}")
    private String secretKey;

    public void validarToken(String token){
        token = token.replace("Barear","");
    }

}
