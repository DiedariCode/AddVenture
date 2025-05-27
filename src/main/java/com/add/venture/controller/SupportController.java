package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.add.venture.dto.ContactoDTO;
import com.add.venture.helper.UsuarioAutenticadoHelper;

@Controller
@RequestMapping("/support")
public class SupportController {

    @Autowired
    UsuarioAutenticadoHelper usuarioAutenticadoHelper;

    @GetMapping
    public String mostrarVistaAyuda(Model model) {
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        return "support/help";
    }

    @PostMapping()
    public String procesarFormulario(@ModelAttribute ContactoDTO contacto, Model model) {
        // Aquí puedes guardar el mensaje, enviarlo por correo, etc.
        System.out.println("Mensaje recibido de: " + contacto.getNombre());
        System.out.println("Email: " + contacto.getEmail());
        System.out.println("Asunto: " + contacto.getAsunto());
        System.out.println("Categoría: " + contacto.getCategoria());
        System.out.println("Mensaje: " + contacto.getMensaje());

        return "redirect:/support?success=true";
    }

}
