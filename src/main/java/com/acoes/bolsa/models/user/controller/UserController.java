package com.acoes.bolsa.models.user.controller;


import com.acoes.bolsa.auth.AuthenticateUseCase;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        try {
            AuthenticateUseCase auth = new AuthenticateUseCase(userRepository);
            String token = auth.execute(user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario ou senha incorreto");
        }
        catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro interno no servidor");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> criarUser (@Valid @RequestBody UserEntity userEntity){
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

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUser (@PathVariable UUID id, UserEntity userEntity){

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
