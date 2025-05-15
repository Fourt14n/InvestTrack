package com.acoes.bolsa.models.acoes;

import lombok.Data;

@Data
public class UniqueStock {
	String symbol;
	long marketCap;
	String shortName;
	String longName;
	double regularMarketChange;
	double regularMarketChangePercent;
	double regularMarketPrice;
	long regularMarketVolume;
	String logourl;
}
