package com.acoes.bolsa.models.user.controller;

import com.acoes.bolsa.models.user.dto.AuthUserRequestDTO;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import com.acoes.bolsa.models.user.useCase.AuthenticateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticateUseCase authenticateUseCase;

    @Tag(name = "Rotas de usuários")
    @Operation(summary = "Faz login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Usuário para ser criado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class),
                    examples = @ExampleObject(value = """
                                        {
                                        "email": "ferdinando@email.com",
                                        "password": "12345678"
                            }
                            """))
    )

                                   @RequestBody AuthUserRequestDTO authUserRequestDTO) {
        try {
            var token = this.authenticateUseCase.execute(authUserRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario ou senha incorreto");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro interno no servidor");
        }
    }
}
