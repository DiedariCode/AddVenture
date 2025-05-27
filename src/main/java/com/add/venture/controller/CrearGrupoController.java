package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.add.venture.dto.CrearGrupoViajeDTO;
import com.add.venture.helper.UsuarioAutenticadoHelper;
import com.add.venture.service.IGrupoViajeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/crear-grupo")
public class CrearGrupoController {

    @Autowired
    private UsuarioAutenticadoHelper usuarioAutenticadoHelper;
    
    @Autowired
    private IGrupoViajeService grupoViajeService;
    
    @GetMapping
    public String mostrarFormularioCreacion(Model model) {
        // Cargar datos del usuario para la navbar y perfil
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        
        // Añadir el DTO para el formulario si no existe
        if (!model.containsAttribute("datosViaje")) {
            model.addAttribute("datosViaje", new CrearGrupoViajeDTO());
    }

        // Cargar tipos de viaje para el selector
        model.addAttribute("tiposViaje", grupoViajeService.obtenerTiposViaje());
        
        return "grupos/crear";
    }
    
    @PostMapping
    public String procesarCreacion(@Valid @ModelAttribute("datosViaje") CrearGrupoViajeDTO dto, 
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        
        // Verificar errores de validación
        if (result.hasErrors()) {
            // Cargar datos del usuario para la navbar y perfil
            usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
            usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
            
            // Cargar tipos de viaje para el selector
            model.addAttribute("tiposViaje", grupoViajeService.obtenerTiposViaje());
            
            return "grupos/crear";
}

        try {
            // Crear el grupo de viaje
            grupoViajeService.crearGrupoViaje(dto);
            
            // Añadir mensaje de éxito
            redirectAttributes.addFlashAttribute("mensaje", "Grupo de viaje creado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            
            return "redirect:/grupos";
        } catch (Exception e) {
            // Manejar errores
            model.addAttribute("error", "Error al crear el grupo: " + e.getMessage());
            
            // Cargar datos del usuario para la navbar y perfil
            usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
            usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
            
            // Cargar tipos de viaje para el selector
            model.addAttribute("tiposViaje", grupoViajeService.obtenerTiposViaje());
            
            return "grupos/crear";
        }
    }
}