package com.acoes.bolsa.models.user.controller;


import com.acoes.bolsa.useCase.AuthenticateUseCase;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import com.acoes.bolsa.useCase.CreateUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CreateUser createUser;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        try {
            AuthenticateUseCase auth = new AuthenticateUseCase(userRepository, passwordEncoder);
            String token = auth.execute(user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario ou senha incorreto");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro interno no servidor");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> criarUser(@Valid @RequestBody UserEntity userEntity) {
        try {
            var result = this.createUser.execute(userEntity);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
