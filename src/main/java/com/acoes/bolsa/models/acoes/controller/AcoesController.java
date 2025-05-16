package com.acoes.bolsa.models.acoes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acoes.bolsa.models.acoes.Stock;
import com.acoes.bolsa.models.acoes.UniqueStock;
import com.acoes.bolsa.models.acoes.AcoesModel;

import com.acoes.bolsa.service.StockService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/acoes")
public class AcoesController {
	private StockService stockServ = new StockService();
	
	@GetMapping("/altas")
	public String listarAltas(){
		try {
			AcoesModel acao = new AcoesModel();
			
			ResponseEntity<?> resposta = stockServ.buscarAltas();
			
			// Valido se a resposta teve algo de errado, difícil pois ela está bem configurada e não depende do usuário
			if(resposta.getStatusCode() != HttpStatus.OK) {
				return "Algo deu errado: " + resposta.getBody();
			}
			
			// Crio  e configuro o object mapper para desserializar os objetos
			ObjectMapper objMap = new ObjectMapper();
			objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			// Crio "NÓS" de JSON para puxar informações específicar e popular um objeto
			JsonNode root = objMap.readTree(resposta.getBody().toString());
			JsonNode stocks = root.at("/stocks");
			JsonNode availableSectors = root.at("/availableSectors");
			
			// Então eu monto o objeto com as informações recebidas
			acao.stocks = objMap.convertValue(stocks, new TypeReference<List<Stock>>() {});
			acao.availableSectors = objMap.convertValue(availableSectors, new TypeReference<List<String>>() {});
			
			// Transformo o objeto em um JSON e retorno ele
			String resultado = objMap.writeValueAsString(acao);
			
			
			
			return resultado;
			
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GetMapping("/baixas")
	public String listarBaixas(){
		try {
			AcoesModel acao = new AcoesModel();
			
			ResponseEntity<?> resposta = stockServ.buscarBaixas();
			
			if(resposta.getStatusCode() != HttpStatus.OK) {
				return "Algo deu errado: " + resposta.getBody();
			}
			
			ObjectMapper objMap = new ObjectMapper();
			objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			JsonNode root = objMap.readTree(resposta.getBody().toString());
			JsonNode stocks = root.at("/stocks");
			JsonNode availableSectors = root.at("/availableSectors");
			
			
			acao.stocks = objMap.convertValue(stocks, new TypeReference<List<Stock>>() {});
			acao.availableSectors = objMap.convertValue(availableSectors, new TypeReference<List<String>>() {});
			
			String resultado = objMap.writeValueAsString(acao);
			
			
			
			return resultado;
			
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/populares")
	public String listarPopulares(){
		try {
			AcoesModel acao = new AcoesModel();
			
			ResponseEntity<?> resposta = stockServ.buscarPopulares();
			
			if(resposta.getStatusCode() != HttpStatus.OK) {
				return "Algo deu errado: " + resposta.getBody();
			}
			
			ObjectMapper objMap = new ObjectMapper();
			objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			JsonNode root = objMap.readTree(resposta.getBody().toString());
			JsonNode stocks = root.at("/stocks");
			JsonNode availableSectors = root.at("/availableSectors");
			
			
			acao.stocks = objMap.convertValue(stocks, new TypeReference<List<Stock>>() {});
			acao.availableSectors = objMap.convertValue(availableSectors, new TypeReference<List<String>>() {});
			
			String resultado = objMap.writeValueAsString(acao);
			
			
			
			return resultado;
			
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GetMapping("/{ticker}")
	public String buscarAcao(@PathVariable("ticker") String ticker) {
		try {
			
			List<UniqueStock> stock;
			
			ResponseEntity<?> resposta = stockServ.buscarAcao(ticker);
			
			if(resposta.getStatusCode() != HttpStatus.OK) {
				return "Algo deu errado: " + resposta.getBody();
			}
			
			ObjectMapper objMap = new ObjectMapper();
			objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			JsonNode root = objMap.readTree(resposta.getBody().toString());
			JsonNode stocks = root.at("/results");
			
			stock = objMap.convertValue(stocks, new TypeReference<List<UniqueStock>>() {});
			
			String resultado = objMap.writeValueAsString(stock.get(0));
			
			return resultado;
			
		} catch (Exception e) {
			return "Ocorreu um erro interno: " + e.getMessage();
		}
	}
}
