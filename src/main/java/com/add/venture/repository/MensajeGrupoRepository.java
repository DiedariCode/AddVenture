package com.add.venture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.add.venture.model.GrupoViaje;
import com.add.venture.model.MensajeGrupo;

public interface MensajeGrupoRepository extends JpaRepository<MensajeGrupo, Long> {
    
    /**
     * Busca mensajes de un grupo
     * 
     * @param grupo el grupo cuyos mensajes se buscan
     * @return lista de mensajes del grupo
     */
    List<MensajeGrupo> findByGrupoOrderByFechaEnvioAsc(GrupoViaje grupo);
}