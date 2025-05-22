package com.acoes.bolsa.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acoes.bolsa.models.favorito.repository.AcaoFavoritaRepository;
import com.acoes.bolsa.models.acoes.AcaoFavorita;
import com.acoes.bolsa.models.user.entity.UserEntity;
import com.acoes.bolsa.models.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;

@Service
public class FavoritosServices {
    
    @Autowired
    private AcaoFavoritaRepository acaoFavoritaRepository;
    
    @Autowired
    private UserRepository userRepository;

    public void addFavorite(UUID userId, String actionCode) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        if (acaoFavoritaRepository.existsByUserAndActionCode(user, actionCode)) {
            throw new IllegalArgumentException("Esta ação já está na lista de favoritos");
        }
        
        AcaoFavorita fav = new AcaoFavorita();
        fav.setUser(user);
        fav.setActionCode(actionCode);
        acaoFavoritaRepository.save(fav);
    }

    @Transactional
    public void removeFavorite(UUID userId, String actionCode) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        AcaoFavorita favorita = acaoFavoritaRepository.findByUserAndActionCode(user, actionCode)
            .orElseThrow(() -> new IllegalArgumentException("Ação favorita não encontrada"));
        
        acaoFavoritaRepository.delete(favorita);
    }

    public List<String> getFavorites(UUID userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        return acaoFavoritaRepository.findByUser(user).stream()
                .map(AcaoFavorita::getActionCode)
                .toList();
    }
}