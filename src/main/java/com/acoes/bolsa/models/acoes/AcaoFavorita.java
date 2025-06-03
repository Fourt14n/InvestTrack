package com.acoes.bolsa.models.acoes;

import jakarta.persistence.*;
import lombok.Data;

import com.acoes.bolsa.models.user.entity.UserEntity;

@Data
@Entity
@Table(name = "favoritos")
public class AcaoFavorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favId", unique = true)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "favUser")
    private UserEntity user;
    
    @Column(name = "favActionCode")
    private String actionCode; // Ex: PETR4, VALE3

    public AcaoFavorita() {}
}