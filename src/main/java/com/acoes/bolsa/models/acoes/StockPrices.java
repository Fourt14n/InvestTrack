package com.acoes.bolsa.models.acoes;

import lombok.Data;

@Data
public class StockPrices {
	double open; // Abertura do dia
	double high; // Alta do dia
	double low; // Baixa do dia
	double close; // Fechamento do dia
	long date;
}
