package com.add.venture.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.add.venture.dto.ViajeDTO;
import com.add.venture.repository.ActividadRepository;
import com.add.venture.service.ViajeService;

@Controller
@RequestMapping("/grupos")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    @Autowired
    private ActividadRepository actividadRepository;

    /**
     * Muestra la página de crear viaje
     */
    @GetMapping("/crear")
    public String mostrarFormularioDeCreacion(Model model) {
        model.addAttribute("viajeDTO", new ViajeDTO()); // Añadir un objeto vacío para la vista
        model.addAttribute("actividadesDisponibles", actividadRepository.findAll()); // Añadir actividades disponibles
        return "user/creaTuGrupo"; // Retorna el nombre de la vista
    }

    /**
     * Procesa el formulario de creación de viaje
     */
    @PostMapping("/crear")
    public String crearViaje(@ModelAttribute("viajeDTO") ViajeDTO viajeDTO)
    {
        // Lógica para guardar el viaje y la imagen
        viajeService.crearViaje(viajeDTO); 
        return "redirect:/explore"; // Redirige a la lista de viajes después de crear uno nuevo
    }
}
