package com.acoes.bolsa.repository;

import com.acoes.bolsa.models.AcaoFavorita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AcaoFavoritaRepository extends JpaRepository<AcaoFavorita, Long> {
	
	//pra buscar as ações favoritas de um usuario
    List<AcaoFavorita> findByUserId(Long userId);
    
    //pra deletar uma ação de acordo com o ID do usuario e a acao 
    void deleteByUserIdAndActionCode(Long userId, String actionCode);
    
    //verifica se uma acao favorita existe com mbase no id do usuario 
    boolean existsByUserIdAndActionCode(Long userId, String actionCode);
}
