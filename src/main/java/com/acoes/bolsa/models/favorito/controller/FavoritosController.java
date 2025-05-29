package com.acoes.bolsa.models.favorito.controller;

import com.acoes.bolsa.service.FavoritosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
public class FavoritosController {

    @Autowired
    private FavoritosServices service;

    @PostMapping("/{actionCode}")
    public ResponseEntity<?> adicionarFavorito(
            Authentication authentication,
            @PathVariable String actionCode) {

        UUID userId = UUID.fromString(authentication.getName());
        service.addFavorite(userId, actionCode);
        return ResponseEntity.ok().body(Map.of(
                "message", "Ação adicionada aos favoritos",
                "acao", actionCode
        ));
    }

    @DeleteMapping("/{actionCode}")
    public ResponseEntity<?> removerFavorito(
            Authentication authentication,
            @PathVariable String actionCode) {

        UUID userId = UUID.fromString(authentication.getName());
        service.removeFavorite(userId, actionCode);
        return ResponseEntity.ok().body(Map.of(
                "message", "Ação removida dos favoritos",
                "acao", actionCode
        ));
    }

    @GetMapping
    public ResponseEntity<List<String>> listarFavoritos(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(service.getFavorites(userId));
    }
}