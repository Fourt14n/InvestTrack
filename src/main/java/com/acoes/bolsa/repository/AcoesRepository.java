package com.acoes.bolsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acoes.bolsa.models.AcoesModel;

@Repository
public interface AcoesRepository extends JpaRepository<AcoesModel, Long> {

}
