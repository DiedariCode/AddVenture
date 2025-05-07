package com.add.venture.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.add.venture.dto.DiaItinerarioDTO;
import com.add.venture.dto.ViajeDTO;
import com.add.venture.model.Actividad;
import com.add.venture.model.DiasItinerario;
import com.add.venture.model.Viaje;
import com.add.venture.repository.ActividadRepository;
import com.add.venture.repository.UsuarioRepository;
import com.add.venture.repository.ViajeRepository;

import jakarta.transaction.Transactional;

@Service
public class ViajeServiceImpl implements ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Override
    @Transactional // SI SALE MAL, HACER ROLLBACK, NO SE GUARDA NADA EN LA BASE DE DATOS
    public Viaje crearViaje(ViajeDTO viajeDTO) {

        // Crear un nuevo objeto Viaje a partir del DTO
        Viaje viaje = new Viaje();
        viaje.setTitulo(viajeDTO.getTitulo());
        viaje.setDestino(viajeDTO.getDestino());
        viaje.setTipoViaje(viajeDTO.getTipoViaje());
        viaje.setFechaInicio(viajeDTO.getFechaInicio());
        viaje.setFechaFin(viajeDTO.getFechaFin());
        viaje.setTamanoGrupo(viajeDTO.getTamanoGrupo());
        viaje.setPresupuesto(viajeDTO.getPresupuesto());
        viaje.setDescripcion(viajeDTO.getDescripcion());

        // Convertir DiaItinerarioDTO a DiaItinerario y asignar a viaje
        List<DiasItinerario> itinerario = new ArrayList<>();
        for (DiaItinerarioDTO dto : viajeDTO.getItinerario()) { // Para cada elemento dto dentro de la lista viajeDTO.getItinerario(), haz lo siguiente
            DiasItinerario dia = new DiasItinerario();
            dia.setNumeroDia(dto.getNumeroDia());
            dia.setDescripcion(dto.getDescripcion());
            dia.setViaje(viaje); // Establecer la relación con el viaje
            itinerario.add(dia);
        }

        viaje.setItinerario(itinerario);

        Set<Actividad> actividades = new HashSet<>();
        for (String nombre : viajeDTO.getActividades()) {
            Actividad actividad = actividadRepository
                    .findByNombre(nombre)
                    .orElseThrow(() -> new RuntimeException("Actividad no encontrada: " + nombre));
            actividades.add(actividad);
        }
        viaje.setActividades(actividades);
        viaje.setRequisitos(viajeDTO.getRequisitos());
        viaje.setNotificacionesUnirse(viajeDTO.isNotificacionesUnirse());
        viaje.setPermitirChatDirecto(viajeDTO.isPermitirChatDirecto());


        // Guardar el viaje en la base de datos
        Viaje viajeGuardado = viajeRepository.save(viaje);

        return viajeGuardado;
    }
}
