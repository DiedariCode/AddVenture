package com.add.venture.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.add.venture.dto.CrearGrupoViajeDTO;
import com.add.venture.dto.DiaItinerarioDTO;
import com.add.venture.model.Etiqueta;
import com.add.venture.model.GrupoViaje;
import com.add.venture.model.Itinerario;
import com.add.venture.model.ParticipanteGrupo;
import com.add.venture.model.TipoViaje;
import com.add.venture.model.Usuario;
import com.add.venture.model.Viaje;
import com.add.venture.model.ParticipanteGrupo.EstadoSolicitud;
import com.add.venture.repository.EtiquetaRepository;
import com.add.venture.repository.GrupoViajeRepository;
import com.add.venture.repository.TipoViajeRepository;
import com.add.venture.repository.UsuarioRepository;
import com.add.venture.repository.ViajeRepository;

@Service
public class GrupoViajeServiceImpl implements IGrupoViajeService {

    @Autowired
    private GrupoViajeRepository grupoViajeRepository;
    
    @Autowired
    private ViajeRepository viajeRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TipoViajeRepository tipoViajeRepository;
    
    @Autowired
    private EtiquetaRepository etiquetaRepository;
    
    @Override
    @Transactional
    public GrupoViaje crearGrupoViaje(CrearGrupoViajeDTO dto) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario creador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Crear el viaje
        Viaje viaje = new Viaje();
        viaje.setDestinoPrincipal(dto.getDestinoPrincipal());
        viaje.setFechaInicio(dto.getFechaInicio());
        viaje.setFechaFin(dto.getFechaFin());
        viaje.setDescripcion(dto.getDescripcion());
        viaje.setPuntoEncuentro(dto.getPuntoEncuentro());
        viaje.setImagenDestacada(dto.getImagenDestacada());
        viaje.setRangoEdadMin(dto.getRangoEdadMin());
        viaje.setRangoEdadMax(dto.getRangoEdadMax());
        viaje.setFechaCreacion(LocalDateTime.now());
        viaje.setEsVerificado(false);
        viaje.setEstado("activo");
        
        // Asignar tipo de viaje si se especificó
        if (dto.getIdTipoViaje() != null) {
            TipoViaje tipoViaje = tipoViajeRepository.findById(dto.getIdTipoViaje())
                    .orElseThrow(() -> new RuntimeException("Tipo de viaje no encontrado"));
            viaje.setTipo(tipoViaje);
        }
        
        // Guardar el viaje
        viaje = viajeRepository.save(viaje);
        
        // Crear el grupo de viaje
        GrupoViaje grupo = new GrupoViaje();
        grupo.setNombreViaje(dto.getNombreViaje());
        grupo.setTipoGrupo(dto.getTipoGrupo());
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setEstado("activo");
        grupo.setCreador(creador);
        grupo.setViaje(viaje);
        
        // Guardar el grupo inicialmente para obtener su ID
        grupo = grupoViajeRepository.save(grupo);
        
        // Procesar etiquetas si se especificaron
        if (dto.getEtiquetas() != null && !dto.getEtiquetas().isEmpty()) {
            Set<Etiqueta> etiquetas = new HashSet<>();
            for (String nombreEtiqueta : dto.getEtiquetas()) {
                Etiqueta etiqueta = etiquetaRepository.findByNombreEtiqueta(nombreEtiqueta)
                        .orElseGet(() -> {
                            Etiqueta nuevaEtiqueta = new Etiqueta();
                            nuevaEtiqueta.setNombreEtiqueta(nombreEtiqueta);
                            return etiquetaRepository.save(nuevaEtiqueta);
                        });
                etiquetas.add(etiqueta);
            }
            grupo.setEtiquetas(etiquetas);
        }
        
        // Guardar el grupo con las etiquetas
        grupo = grupoViajeRepository.save(grupo);
        
        // Añadir al creador como participante
        ParticipanteGrupo participante = new ParticipanteGrupo();
        participante.setUsuario(creador);
        participante.setGrupo(grupo);
        participante.setRolParticipante("CREADOR");
        participante.setEstadoSolicitud(EstadoSolicitud.ACEPTADO);
        participante.setFechaUnion(LocalDateTime.now());
        
        // Procesar itinerario si se especificó
        if (dto.getDiasItinerario() != null && !dto.getDiasItinerario().isEmpty()) {
            Set<Itinerario> itinerarios = new HashSet<>();
            for (DiaItinerarioDTO diaDTO : dto.getDiasItinerario()) {
                Itinerario itinerario = new Itinerario();
                itinerario.setDiaNumero(diaDTO.getDiaNumero());
                itinerario.setTitulo(diaDTO.getTitulo());
                itinerario.setDescripcion(diaDTO.getDescripcion());
                itinerario.setPuntoPartida(diaDTO.getPuntoPartida());
                itinerario.setPuntoLlegada(diaDTO.getPuntoLlegada());
                itinerario.setDuracionEstimada(diaDTO.getDuracionEstimada());
                itinerario.setGrupo(grupo);
                itinerarios.add(itinerario);
            }
            grupo.setItinerarios(itinerarios);
        }
        
        // Guardar el grupo con todas sus relaciones
        return grupoViajeRepository.save(grupo);
    }

    @Override
    public boolean viajeYaAsignado(Long idViaje) {
        return grupoViajeRepository.existsByViajeIdViaje(idViaje);
    }

    @Override
    public List<TipoViaje> obtenerTiposViaje() {
        return tipoViajeRepository.findAll();
    }
}