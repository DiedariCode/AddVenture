package com.add.venture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.add.venture.helper.UsuarioAutenticadoHelper;
import com.add.venture.repository.GrupoViajeRepository;

@Controller
@RequestMapping("/grupos")
public class GrupoViajeController {

    @Autowired
    private UsuarioAutenticadoHelper usuarioAutenticadoHelper;
    
    @Autowired
    private GrupoViajeRepository grupoViajeRepository;
    
    @GetMapping
    public String listarGrupos(Model model) {
        // Cargar datos del usuario para la navbar y perfil
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        
        // Cargar todos los grupos
        model.addAttribute("grupos", grupoViajeRepository.findAll());
        
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