package com.acoes.bolsa.models.acoes;

import jakarta.persistence.*;
import lombok.Data;

import com.acoes.bolsa.models.user.entity.UserEntity;

@Data
@Entity (name = "favoritos")
public class AcaoFavorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    private String actionCode; // Ex: PETR4, VALE3

    public AcaoFavorita() {}
}