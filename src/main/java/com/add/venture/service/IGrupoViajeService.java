package com.add.venture.service;

import java.util.List;

import com.add.venture.dto.CrearGrupoViajeDTO;
import com.add.venture.model.GrupoViaje;
import com.add.venture.model.TipoViaje;

public interface IGrupoViajeService {
    
    /**
     * Crea un nuevo grupo de viaje con su viaje asociado
     * 
     * @param dto datos para la creación del grupo y viaje
     * @return el grupo creado
     * @throws RuntimeException si el viaje ya está asignado a otro grupo
     */
    GrupoViaje crearGrupoViaje(CrearGrupoViajeDTO dto);
    
    /**
     * Verifica si un viaje ya está asignado a un grupo
     * 
     * @param idViaje el ID del viaje a verificar
     * @return true si el viaje ya está asignado, false en caso contrario
     */
    boolean viajeYaAsignado(Long idViaje);
    
    /**
     * Obtiene todos los tipos de viaje disponibles
     * 
     * @return lista de tipos de viaje
     */
    List<TipoViaje> obtenerTiposViaje();
}