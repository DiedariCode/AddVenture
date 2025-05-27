package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.add.venture.helper.UsuarioAutenticadoHelper;
import com.add.venture.repository.GrupoViajeRepository;
import com.add.venture.service.IGrupoViajeService;

@Controller
@RequestMapping("/grupos")
public class GrupoViajeController {

    @Autowired
    private UsuarioAutenticadoHelper usuarioAutenticadoHelper;
    
    @Autowired
    private GrupoViajeRepository grupoViajeRepository;
    
    @Autowired
    private IGrupoViajeService grupoViajeService;
    
    @GetMapping
    public String listarGrupos(
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) Long tipoViaje,
            @RequestParam(required = false) String tipoGrupo,
            @RequestParam(required = false) String rangoEdad,
            @RequestParam(required = false) Boolean verificado,
            @RequestParam(required = false) String etiquetas,
            @RequestParam(required = false) String ordenar,
            Model model) {
        
        // Cargar datos del usuario para la navbar y perfil
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        
        // Cargar todos los grupos (en una implementación real, aplicaríamos los filtros)
        model.addAttribute("grupos", grupoViajeRepository.findAll());
        
        // Cargar tipos de viaje para los filtros
        model.addAttribute("tiposViaje", grupoViajeService.obtenerTiposViaje());
        
        return "grupos/buscar";
    }
    
    @GetMapping("/{id}")
    public String verDetallesGrupo(@PathVariable("id") Integer idGrupo, Model model) {
        // Cargar datos del usuario para la navbar y perfil
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        
        // Cargar el grupo
        model.addAttribute("grupo", grupoViajeRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado")));
        
        return "grupos/detalles";
    }
}