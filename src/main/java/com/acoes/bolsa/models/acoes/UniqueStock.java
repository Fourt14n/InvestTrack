package com.acoes.bolsa.models.acoes;

import java.util.ArrayList;

import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class UniqueStock {
	String symbol;
	String currency;
	long marketCap;
	String shortName;
	String longName;
	double regularMarketChange;
	double regularMarketChangePercent;
	double regularMarketPrice;
	double regularMarketDayHigh;
	double regularMarketDayLow;
	long regularMarketVolume;
	ArrayList<StockPrices> historicalDataPrice;
	String logourl;
}
