package com.acoes.bolsa.auth;

import com.acoes.bolsa.models.user.dto.AuthUserRequestDTO;
import com.acoes.bolsa.models.user.dto.AuthUserResponseDTO;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Service
public class AuthenticateUseCase {

    @Autowired
    private final UserRepository userRepository;
    @Value("{security.token.secret.user}")
    private  String secretKey;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticateUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthUserResponseDTO execute(AuthUserRequestDTO authUserRequestDTO) throws
            AuthenticationException {
        var user = this.userRepository.findByEmailAndPassword(authUserRequestDTO.email(), authUserRequestDTO.password())
                .orElseThrow(() -> new UsernameNotFoundException("Username/Senha incorretos"));
        var passwordMatches = this.passwordEncoder
                .matches(authUserRequestDTO.password(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expireIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("java")
                .withSubject(user.getId().toString())
                .withClaim("roles", Arrays.asList("USER"))
                .withIssuedAt(expireIn)
                .sign(algorithm);

        var authUserResponse = AuthUserResponseDTO.builder()
                .access_token(token)
                .expires_in(expireIn.toEpochMilli())
                .build();
        return authUserResponse;
    }

}
