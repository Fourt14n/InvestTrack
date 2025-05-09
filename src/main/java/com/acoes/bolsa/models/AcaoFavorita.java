package com.acoes.bolsa.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AcaoFavorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String actionCode; // Ex: PETR4, VALE3

    public AcaoFavorita() {}
}
