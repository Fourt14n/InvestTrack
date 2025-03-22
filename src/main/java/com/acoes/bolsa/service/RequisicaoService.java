package com.acoes.bolsa.service;

import java.util.Dictionary;
import java.util.Enumeration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.acoes.bolsa.models.AcoesModel;

public class RequisicaoService {
	static private RestTemplate restTemp = new RestTemplate();
	static private String urlBase = "https://brapi.dev/api/";
	
	public static Dictionary<ResponseEntity<?>, AcoesModel> executeRequest(String endpoint, String filtros) {
		Dictionary<ResponseEntity<?>, AcoesModel> acoes = new Dictionary<ResponseEntity<?>, AcoesModel>() {
			
			@Override
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public AcoesModel remove(Object key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AcoesModel put(ResponseEntity<?> key, AcoesModel value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<ResponseEntity<?>> keys() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public AcoesModel get(Object key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<AcoesModel> elements() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		String url = urlBase + filtros;
		
		acoes.put(restTemp.getForEntity(url, String.class), restTemp.getForObject(url, AcoesModel.class));
		return acoes;
	}
	
}
