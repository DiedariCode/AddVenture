package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.add.venture.dto.PerfilUsuarioDTO;
import com.add.venture.helper.UsuarioAutenticadoHelper;
import com.add.venture.service.IUsuarioService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioAutenticadoHelper usuarioHelper;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public String mostrarVistaPerfil() {
        return "user/perfil";
    }

    @GetMapping("/configuracion")
    public String mostrarVistaConfiguracion(Model model) {
        usuarioHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioHelper.cargarUsuarioParaPerfil(model);
        

        return "user/configuracion";
    }

    @PostMapping("/configuracion")
    public String actualizarConfiguracion(@ModelAttribute("usuario") PerfilUsuarioDTO perfilDto, Model model) {
        // Aquí validas y guardas los datos actualizados
        usuarioService.actualizarPerfil(perfilDto);

        // Recargar datos para el navbar y perfil con la info actualizada
        usuarioHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioHelper.cargarUsuarioParaPerfil(model);

        model.addAttribute("mensaje", "Perfil actualizado correctamente");

        return "user/configuracion";
    }
}
