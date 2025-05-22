package com.acoes.bolsa.favoritos.repository;

import com.acoes.bolsa.models.acoes.AcaoFavorita;
import com.acoes.bolsa.models.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AcaoFavoritaRepository extends JpaRepository<AcaoFavorita, Long> {
    
    List<AcaoFavorita> findByUser(UserEntity user);
    
    Optional<AcaoFavorita> findByUserAndActionCode(UserEntity user, String actionCode);
    
    boolean existsByUserAndActionCode(UserEntity user, String actionCode);
    
    void deleteByUserAndActionCode(UserEntity user, String actionCode);
}