package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.add.venture.dto.RegistroUsuarioDTO;
import com.add.venture.service.UsuarioService;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String indexForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Mostrar toda la información en consola para depurar
        System.out.println("Authentication class: " + authentication.getClass().getName());
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Credentials: " + authentication.getCredentials());
        System.out.println("Authorities: " + authentication.getAuthorities());
        System.out.println("Details: " + authentication.getDetails());
        

        String correo = authentication.getName(); // Aquí obtienes el username QUE ES EL CORREO

        RegistroUsuarioDTO usuario = usuarioService.buscarPorEmail(correo);
        System.out.println("Name: " + usuario.getNombre());
        model.addAttribute("username", usuario);
        return "index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        return "/grupos/crear";
    }

    @GetMapping("/explore")
    public String exploreForm(Model model) {
        return "/user/exploraViajes";
    }

    @GetMapping("/about")
    public String aboutForm(Model model) {
        return "/user/aboutUs";
    }

    @GetMapping("/profile")
    public String prefilForm(Model model) {
        return "/user/perfilUsuario";
    }

    @GetMapping("/details")
    public String detallesForm(Model model) {
        return "/user/trip-detalles";
    }
}
