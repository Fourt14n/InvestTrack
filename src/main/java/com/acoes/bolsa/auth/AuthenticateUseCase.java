package com.acoes.bolsa.auth;

import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticateUseCase {

    @Autowired
    private final UserRepository userRepository;
    private final String secretKey = "user";
    private final long expiration = 86400000;

    public AuthenticateUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String execute(String email, String password) {
        UserEntity user = userRepository.findByEmailAndPassword(email, password);

        if (user == null) {
            throw new RuntimeException("email ou Senha incorreto");
        }
        return generateToken(user);
    }

    public String generateToken(UserEntity user) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("name", user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
        return token;
    }
}
