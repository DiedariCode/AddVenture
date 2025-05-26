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
import com.add.venture.service.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

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

        // Validar si el nombre de usuario ya existe
        if (usuarioService.existeNombreUsuario(dto.getNombreUsuario())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "auth/registrarse";
        }

        // Si todo está bien, crear el usuario
        usuarioService.crearUsuario(dto);
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso");
        return "redirect:/";
    }
}
