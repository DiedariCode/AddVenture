package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.add.venture.dto.RegistroUsuarioDTO;
import com.add.venture.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new RegistroUsuarioDTO());
        return "auth/registrarse";
    }

    @PostMapping("/registro")
    public String procesarFormulario(@ModelAttribute("usuario") RegistroUsuarioDTO dto,
            RedirectAttributes redirectAttributes,
            Model model) {
        // Validar que contrasena y confirmContrasena sean iguales
        if (!dto.getContrasena().equals(dto.getConfirmContrasena())) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "auth/registrarse"; 
        }

        usuarioService.crearUsuario(dto);
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso");
        return "redirect:/usuarios/registro";
    }
}
