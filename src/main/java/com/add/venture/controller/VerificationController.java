package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.add.venture.dto.VerificationCodeDTO;
import com.add.venture.service.IEmailVerificationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private IEmailVerificationService verificationService;

    // Endpoint para enviar código a un email
    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(@RequestParam String email) {
        verificationService.sendVerificationCode(email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Código enviado correctamente");
        return ResponseEntity.ok(response);
    }

    // Endpoint para verificar código
    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationCodeDTO dto) {
        boolean valid = verificationService.verifyCode(dto.getEmail(), dto.getCode());

        Map<String, Object> response = new HashMap<>();
        if (valid) {
            response.put("verified", true);
            response.put("message", "Email verificado correctamente");
        } else {
            response.put("verified", false);
            response.put("message", "Código incorrecto o expirado");
        }
        return ResponseEntity.ok(response);
    }
}
