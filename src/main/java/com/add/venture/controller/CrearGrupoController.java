package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.add.venture.dto.CrearGrupoViajeDTO;
import com.add.venture.helper.UsuarioAutenticadoHelper;


@Controller
@RequestMapping("/crear-grupo")
public class CrearGrupoController {

    @Autowired
    UsuarioAutenticadoHelper usuarioAutenticadoHelper;
    
    @RequestMapping
    public String crearGrupo(Model model) {
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        return "/grupos/crear"; // This should match the name of your HTML template
    }

    @PostMapping
    public String procesarCreacion(@ModelAttribute ("datosViaje") CrearGrupoViajeDTO dto, Model model) {
        
        return "redirect:/grupos"; 
    }
    
}
