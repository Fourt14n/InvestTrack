package com.acoes.bolsa.models;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class AcoesModel {
	
    public List<stock> stocks; 
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
