package com.acoes.bolsa.models.acoes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acoes.bolsa.models.acoes.Stock;
import com.acoes.bolsa.models.acoes.StockPrices;
import com.acoes.bolsa.models.acoes.UniqueStock;
import com.acoes.bolsa.models.acoes.AcoesModel;

import com.acoes.bolsa.service.StockService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/acoes")
@Tag(name = "Tela inicial", description = "Rotas para popular a tela inicial")
public class AcoesController {
	private StockService stockServ = new StockService();
	
	@Operation(summary = "Puxa as ações em alta, as que tem o maior 'change' as com maior porcentagem de valorização")
	@Tag(name = "Tela inicial")
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
	
	@Operation(summary = "Puxa as ações em baixa, com o menor 'change', as com menor porcentagem de valorização")
	@Tag(name = "Tela inicial")
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
	
	@Operation(summary = "Puxa as ações mais populares, as com o maior 'market_cap', ou seja, as mais compradas")
	@Tag(name = "Tela inicial")
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
	
	@Operation(summary = "Puxa as ações baseado em um pedaço de texto, para pesquisa")
	@Tag(name = "Tela inicial")
	@GetMapping("/pesquisaAcoes/{pesquisa}")
	public List<String> pesquisaAcoes(@PathVariable String pesquisa){
		try {
			ArrayList<String> retorno = new ArrayList<String>();
			
			ResponseEntity<?> resposta = stockServ.pesquisaAcoes(pesquisa);
			
			if(resposta.getStatusCode() != HttpStatus.OK) {
				retorno.add("Algo deu errado: " + resposta.getBody());
				return retorno;
			}
			
			ObjectMapper objMap = new ObjectMapper();
			objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			JsonNode root = objMap.readTree(resposta.getBody().toString());
			JsonNode stocks = root.at("/stocks");
			
			retorno = objMap.convertValue(stocks, new TypeReference<ArrayList<String>>() {});
			
			return retorno;
			
			
		} catch (Exception e) {
			ArrayList<String> retornoErro = new ArrayList<String>();
			retornoErro.add(e.getMessage());
			return retornoErro;
		}
	}
	
	@Operation(summary = "Puxa uma ação pelo valor de ticker dela através da URL, EX: itub3, PETR4")
	@Tag(name = "Ação específica", description = "Rota para puxar uma ação específica, para gerar os cards e as telas específicas por ação")
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
