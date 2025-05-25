package com.add.venture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class SesionController {

    @GetMapping("/login")
    public String mostrarFormularioDeLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMensaje", "Usuario o contraseña incorrectos");
        }

        if (logout != null) {
            model.addAttribute("logoutMensaje", "Has cerrado sesión correctamente");
        }

        return "auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/auth/login?logout=true";
    }
}
