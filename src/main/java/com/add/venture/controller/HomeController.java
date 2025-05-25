package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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

        // Solo proceder si está autenticado y no es "anonymousUser"
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {

            String correo = authentication.getName(); // correo del usuario autenticado
            RegistroUsuarioDTO usuario = usuarioService.buscarPorEmail(correo);

            if (usuario != null) {

                String iniciales = usuario.getIniciales();
                model.addAttribute("iniciales", iniciales);
                model.addAttribute("username", usuario);
            } else {
                System.out.println("Usuario no encontrado con correo: " + correo);
            }
        } else {
            System.out.println("No hay usuario autenticado");
        }

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
