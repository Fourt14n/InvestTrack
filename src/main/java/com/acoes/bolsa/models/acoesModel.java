package com.acoes.bolsa.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class AcoesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@OneToMany
    public List<stock> stocks;
    @OneToMany
    public List<sectors> availableSectors;
	
    public class stock{
    	String stock;
    	String name;
    	double close;
    	double change;
    	int volume;
    	double market_cap;
    	String logo;
    	String sector;
    	String type;
    }
    
    public class sectors{
    	String nome;
    }



}
