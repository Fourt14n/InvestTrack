package com.acoes.bolsa.useCase;

import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class   AuthenticateUseCase {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final String secretKey = "user";
    private final long expiration = 86400000;

    public AuthenticateUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(String email, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty() || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new RuntimeException("Email ou senha incorretos");
        }

        return generateToken(optionalUser.get());
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
