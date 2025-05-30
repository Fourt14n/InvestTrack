package com.acoes.bolsa.models.user.controller;


import com.acoes.bolsa.models.user.dto.AuthUserRequestDTO;
import com.acoes.bolsa.models.user.dto.ProfileCandidateResponseDTO;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;

import com.acoes.bolsa.models.user.useCase.AuthenticateUseCase;
import com.acoes.bolsa.models.user.useCase.CreateUserUseCase;
import com.acoes.bolsa.models.user.useCase.ProfileUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@Tag(name = "Rotas de usuários", description = "Todas as rotas relacionadas ao gerenciamento de usuários")
public class UserController {

    @Autowired
    private ProfileUserUseCase profileUserUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

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
            var result = this.createUserUseCase.execute(userEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu  dsadsadsdas um erro interno em nosso servidor");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> buscarUser(HttpServletRequest request){
        var idUser = request.getAttribute("user_id");
        try {
            var profile = this.profileUserUseCase.execute(UUID.fromString(idUser.toString()));
            return ResponseEntity.ok().body(profile);
        }   catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
