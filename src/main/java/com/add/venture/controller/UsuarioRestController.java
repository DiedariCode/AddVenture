package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.add.venture.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/existe-username")
    public ResponseEntity<Boolean> verificarUsername(@RequestParam String username) {
        boolean existe = usuarioService.existeNombreUsuario(username);
        return ResponseEntity.ok(existe);
    }
}
