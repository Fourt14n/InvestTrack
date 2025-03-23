package com.acoes.bolsa.controller;

import java.util.Dictionary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acoes.bolsa.models.AcoesModel;
import com.acoes.bolsa.service.StockService;

@RestController
@RequestMapping("/acoes")
public class AcoesController {
	private StockService stockServ = new StockService();
	
	@GetMapping("/")
	public String listarAcoes(){
		try {
			AcoesModel acao = new AcoesModel();
			
			ResponseEntity<?> resposta = stockServ.buscarAltas();
			
			return resposta.getBody().toString();
			
			
		} catch (Exception e) {
			return null;
		}
	}

}
