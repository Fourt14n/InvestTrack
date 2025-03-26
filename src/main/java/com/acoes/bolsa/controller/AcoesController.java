package com.acoes.bolsa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acoes.bolsa.models.AcoesModel;
import com.acoes.bolsa.models.Stock;
import com.acoes.bolsa.service.StockService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/acoes")
public class AcoesController {
	private StockService stockServ = new StockService();
	
	@GetMapping("/")
	public String listarAcoes(){
		try {
			AcoesModel acao = new AcoesModel();
			
			ResponseEntity<?> resposta = stockServ.buscarAltas();
			
			ObjectMapper objMap = new ObjectMapper();
			objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			JsonNode root = objMap.readTree(resposta.getBody().toString()); //<--convert the json string to a JsonNode
			JsonNode stocks = root.at("/stocks"); //<-- selecting the "data" part
			JsonNode availableSectors = root.at("/availableSectors");
			//conversion to List<Rating> avoid problems due to list type erasure
			//with the help of jackson TypeReference class
			acao.stocks = objMap.convertValue(stocks, new TypeReference<List<Stock>>() {});
			acao.availableSectors = objMap.convertValue(availableSectors, new TypeReference<List<String>>() {});
			
			String resultado = objMap.writeValueAsString(acao);
			
			
			
			return resultado;
			
			
		} catch (Exception e) {
			return null;
		}
	}

}
