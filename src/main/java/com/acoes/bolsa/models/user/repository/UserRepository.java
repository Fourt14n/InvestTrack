package com.acoes.bolsa.models.user.repository;

import com.acoes.bolsa.models.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional <UserEntity> findByEmailAndPassword(String email, String password);
    Optional <UserEntity> findByUsernameOrEmail (String username, String email);
    Optional <UserEntity> findByEmail (String email);

    Optional<UserEntity> findByUsername(String username);
}
