package com.acoes.bolsa.models.user.controller;


import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "Rotas de usuários", description = "Todas as rotas relacionadas ao gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Tag(name = "Rotas de usuários")
    @Operation(summary = "Cria um usuário, recebendo um objeto como body")
    @PostMapping("/")
    public ResponseEntity<?> criarUser (@io.swagger.v3.oas.annotations.parameters.RequestBody(
    		description = "Usuário para ser criado",
    		content = @Content(mediaType = "application/json", 
    		schema = @Schema(implementation = UserEntity.class),
    		examples = @ExampleObject(value = "{ \"username\": \"ferdinandinho\", \"senha\": \"@12345678\", \"email\": \"ferdinando@email.com\", }"))
    		)
    		
    		@Valid @RequestBody UserEntity userEntity){
        try {
            if (userEntity.getUsername().isEmpty() || userEntity.getEmail().isEmpty() || userEntity.getPassword().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Os campos devem ser preenchidos");
            }
            UserEntity user = userRepository.save(userEntity);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno em nosso servidor");
        }
    }

    @Tag(name = "Rotas de usuários")
    @Operation(summary = "Atualiza um usuário, recebe um id na url e um objeto de usuário no body")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUser (@io.swagger.v3.oas.annotations.parameters.RequestBody(
    		description = "Usuário para ser atualizado",
    		content = @Content(mediaType = "application/json", 
    		schema = @Schema(implementation = UserEntity.class),
    		examples = @ExampleObject(value = "{ \"email\": \"ferdinando@email.com\", \"senha\": \"@12345678\" }"))
    		)
    
    @PathVariable UUID id, UserEntity userEntity){

        try {
            Optional<UserEntity> existeUser = userRepository.findById(id);
            if (existeUser.isPresent()) {
                UserEntity user = existeUser.get();
                user.setEmail(user.getEmail());
                user.setPassword(user.getPassword());

                userRepository.save(user);
                return ResponseEntity.ok("Usuario atualizado");
            }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Nenhum usuario encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno em nosso servidor");
        }
    }



}
