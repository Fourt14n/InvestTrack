package com.acoes.bolsa.service;

import java.util.Dictionary;

import org.springframework.http.ResponseEntity;

import com.acoes.bolsa.models.AcoesModel;

public class StockService {
	private String filtros = "?token=s8qoLMnKWoEoFXMoiAeV57&type=stock";
	private RequisicaoService req = new RequisicaoService();
	
	public Dictionary<ResponseEntity<?>, AcoesModel> buscarAltas() {
		String endpoint = "quote/list/";
		filtros += "&sortBy=change&sortOrder=desc&limit=10";
		
		try {
			
			Dictionary<ResponseEntity<?>, AcoesModel> response = req.executeRequest(endpoint, filtros);
			
			return response;
			
		} catch (Exception e) {
			return null;
		}
		
		
	}
}
