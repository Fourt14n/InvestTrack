package com.acoes.bolsa.models.acoes;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Stock {
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
