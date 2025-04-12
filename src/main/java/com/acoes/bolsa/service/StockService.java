package com.acoes.bolsa.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class StockService {
	private String filtros = "?token=s8qoLMnKWoEoFXMoiAeV57&type=stock";
	private String endpoint = "quote/list";
	
	public ResponseEntity<?> buscarAltas() {
		String filtro = "&sortBy=change&sortOrder=desc&limit=10";
		
		try {
			
			ResponseEntity<?> response = RequisicaoService.executeRequest(endpoint, filtros + filtro);
			
			if(response.getStatusCode() != HttpStatus.OK) {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de requisição indisponível");
			}
			
			return response;
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno: " + e.getMessage());
		}
	}
	
	public ResponseEntity<?> buscarBaixas() {
		String filtro = "&sortBy=change&sortOrder=asc&limit=10";
		
		try {
			
			ResponseEntity<?> response = RequisicaoService.executeRequest(endpoint, filtros + filtro);
			
			if(response.getStatusCode() != HttpStatus.OK) {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de requisição indisponível");
			}
			
			return response;
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno: " + e.getMessage());
		}
	}
	
	public ResponseEntity<?> buscarPopulares() {
		String filtro = "&sortBy=market_cap_basic&sortOrder=desc&limit=10";
		
		try {
			
			ResponseEntity<?> response = RequisicaoService.executeRequest(endpoint, filtros + filtro);
			
			if(response.getStatusCode() != HttpStatus.OK) {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de requisição indisponível");
			}
			
			return response;
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno: " + e.getMessage());
		}
	}

	public ResponseEntity<?> buscarAcao(String ticker){
		endpoint = "quote/ticker";
		endpoint = endpoint.replace("ticker", ticker);
		
		try {
			
			ResponseEntity<?> resposta = RequisicaoService.executeRequest(endpoint, filtros);
			
			if(resposta.getStatusCode() != HttpStatus.OK) {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de requisição indisponível");
			}
			
			return resposta;
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno: " + e.getMessage());
		}
	}
}
