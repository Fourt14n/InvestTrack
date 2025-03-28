package com.acoes.bolsa.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RequisicaoService {
	static private RestTemplate restTemp = new RestTemplate();
	static private String urlBase = "https://brapi.dev/api/";
	
	protected static ResponseEntity<?> executeRequest(String endpoint, String filtros) {
		String url = urlBase + endpoint + filtros;
 		ResponseEntity<?> resposta = restTemp.getForEntity(url, String.class);
		
 		return resposta;
	}
	
}
