package com.acoes.bolsa.models.user.controller;


import com.acoes.bolsa.auth.AuthenticateUseCase;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Rotas de usuários", description = "Todas as rotas relacionadas ao gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
    		
    		@RequestBody UserEntity user) {
        try {
            AuthenticateUseCase auth = new AuthenticateUseCase(userRepository);
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

    @Tag(name = "Rotas de usuários")
    @Operation(summary = "Cria um usuário, recebendo um objeto como body")
    @PostMapping("/")
    public ResponseEntity<?> criarUser (@io.swagger.v3.oas.annotations.parameters.RequestBody(
    		description = "Usuário para ser criado",
    		content = @Content(mediaType = "application/json", 
    		schema = @Schema(implementation = UserEntity.class),
    		examples = @ExampleObject(value = """
                    {
                    "email": "ferdinando@email.com",
                    "password": "12345678",
                    "username": "ferdinando"
    				}
                  """))
    		)
    	@Valid @RequestBody UserEntity userEntity){
        try {
            UserEntity user = userRepository.save(userEntity);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno em nosso servidor");
        }
    }

}
