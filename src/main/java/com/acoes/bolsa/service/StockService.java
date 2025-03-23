package com.acoes.bolsa.service;

import org.springframework.http.ResponseEntity;

public class StockService {
	private String filtros = "?token=s8qoLMnKWoEoFXMoiAeV57&type=stock";
	
	public ResponseEntity<?> buscarAltas() {
		String endpoint = "quote/list";
		filtros += "&sortBy=change&sortOrder=desc&limit=10";
		
		try {
			
			ResponseEntity<?> response = RequisicaoService.executeRequest(endpoint, filtros);
			
			return response;
			
		} catch (Exception e) {
			String erro = e.getMessage();
			return null;
		}
		
		
	}
}
