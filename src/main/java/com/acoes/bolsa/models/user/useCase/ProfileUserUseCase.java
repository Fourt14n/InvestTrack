package com.acoes.bolsa.models.user.useCase;

import com.acoes.bolsa.models.user.dto.ProfileCandidateResponseDTO;
import com.acoes.bolsa.models.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public ProfileCandidateResponseDTO execute(UUID idUser){
        var user = this.userRepository.findById(idUser)
                .orElseThrow(()->{
                    throw new UsernameNotFoundException("User not found");
                });
        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .username(user.getUsername())
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return candidateDTO;
    }
}
