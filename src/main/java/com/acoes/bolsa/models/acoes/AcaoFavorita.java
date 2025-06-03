package com.acoes.bolsa.models.acoes;

import jakarta.persistence.*;
import lombok.Data;

import com.acoes.bolsa.models.user.entity.UserEntity;

@Data
@Entity (name = "favoritos")
@Table(name = "favoritos")
public class AcaoFavorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user")
    @Column(name = "user")
    private UserEntity user;
    
    @Column(name = "actionCode")
    private String actionCode; // Ex: PETR4, VALE3

    public AcaoFavorita() {}
}