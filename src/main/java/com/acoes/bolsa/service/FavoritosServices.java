package com.acoes.bolsa.service;

import com.acoes.bolsa.models.AcaoFavorita;
import com.acoes.bolsa.models.User;
import com.acoes.bolsa.repository.AcaoFavoritaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritosServices {
	
	
    @Autowired
    private AcaoFavoritaRepository repository;

    public void addFavorite(Long userId, String actionCode) {
        if (!repository.existsByUserIdAndActionCode(userId, actionCode)) {
            AcaoFavorita fav = new AcaoFavorita();
            fav.setUser(new User(userId));
            fav.setActionCode(actionCode);
            repository.save(fav);
        }
    }

    @Transactional
    public void removeFavorite(Long userId, String actionCode) {
        boolean exists = repository.existsByUserIdAndActionCode(userId, actionCode);
        
        if (!exists) {
            throw new IllegalArgumentException("Ação favorita não encontrada para o usuário informado.");
        }

        repository.deleteByUserIdAndActionCode(userId, actionCode);
    }


    public List<String> getFavorites(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(AcaoFavorita::getActionCode)
                .toList();
    }
}
