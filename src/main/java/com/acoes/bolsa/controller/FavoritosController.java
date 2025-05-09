package com.acoes.bolsa.controller;


import com.acoes.bolsa.service.FavoritosServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritosController {

    @Autowired
    private FavoritosServices service;

    @PostMapping("/{userId}/{actionCode}")
    public ResponseEntity<?> AdicionarAoFavoritos(@PathVariable Long userId, @PathVariable String actionCode) {
        service.addFavorite(userId, actionCode);
        return ResponseEntity.ok("Ação adicionada aos favoritos");
    }

    @DeleteMapping("/{userId}/{actionCode}")
    public ResponseEntity<?> RemoverDosFavoritos(@PathVariable Long userId, @PathVariable String actionCode) {
    	
    	try {
    		
    		service.removeFavorite(userId, actionCode);
            return ResponseEntity.ok("Ação removida dos favoritos");
			
		} catch (Exception e) {
		
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
        
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<String>> BuscarFvoritosPeloUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getFavorites(userId));
    }
}
