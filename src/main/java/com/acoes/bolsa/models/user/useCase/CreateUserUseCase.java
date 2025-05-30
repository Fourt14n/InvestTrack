package com.acoes.bolsa.models.user.useCase;

import com.acoes.bolsa.exception.UserFoundException;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity execute(UserEntity userEntity){

        System.out.println("chegou aqui");

        this.userRepository
                .findByUsernameOrEmail(userEntity.getUsername(), userEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);
    }
}
