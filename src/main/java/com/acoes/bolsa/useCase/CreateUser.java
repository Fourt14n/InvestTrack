package com.acoes.bolsa.useCase;

import com.acoes.bolsa.exception.UserFoundException;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity execute(UserEntity userEntity){
        this.userRepository
                .findByEmail(userEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);

    }
}
