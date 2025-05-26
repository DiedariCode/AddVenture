package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.add.venture.service.IUsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/existe-username")
    public ResponseEntity<Boolean> verificarUsername(@RequestParam String username) {
        boolean existe = usuarioService.existeNombreUsuario(username);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/validar-username-edicion")
    public ResponseEntity<Boolean> validarUsernameEnEdicion(
            @RequestParam String username,
            @RequestParam String emailActual) {

        boolean usernameEnUsoPorOtro = usuarioService.existeNombreUsuarioExceptoActual(username, emailActual);
        return ResponseEntity.ok(usernameEnUsoPorOtro);
    }

    @GetMapping("/existe-email")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email) {
        boolean existe = usuarioService.existeEmail(email);
        return ResponseEntity.ok(existe);
    }
}
