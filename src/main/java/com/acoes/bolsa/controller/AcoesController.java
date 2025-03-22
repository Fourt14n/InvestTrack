package com.acoes.bolsa.controller;

import java.util.Dictionary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acoes.bolsa.models.AcoesModel;
import com.acoes.bolsa.repository.AcoesRepository;
import com.acoes.bolsa.service.StockService;

@RestController
@RequestMapping("/acoes")
public class AcoesController {
	private StockService stockServ = new StockService();
	
	AcoesRepository repAcoes;
	
	@GetMapping("/")
	public Dictionary<ResponseEntity<?>, AcoesModel> listarAcoes(){
		try {
			AcoesModel acao = new AcoesModel();
			
			Dictionary<ResponseEntity<?>, AcoesModel> resposta = stockServ.buscarAltas();
			
			if(!(resposta.keys().nextElement() == ResponseEntity.ok())) {
				return resposta;
			}
			
			return resposta;
			
			
		} catch (Exception e) {
			return null;
		}
	}

}
