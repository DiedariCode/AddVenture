package com.add.venture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.add.venture.model.GrupoViaje;
import com.add.venture.model.ParticipanteGrupo;
import com.add.venture.model.ParticipanteGrupoId;
import com.add.venture.model.Usuario;

public interface ParticipanteGrupoRepository extends JpaRepository<ParticipanteGrupo, ParticipanteGrupoId> {
    
    /**
     * Verifica si un usuario es participante de un grupo
     * 
     * @param usuario el usuario a verificar
     * @param grupo el grupo a verificar
     * @return true si el usuario es participante del grupo, false en caso contrario
     */
    boolean existsByUsuarioAndGrupo(Usuario usuario, GrupoViaje grupo);
    
    /**
     * Busca un participante por usuario y grupo
     * 
     * @param usuario el usuario a buscar
     * @param grupo el grupo a buscar
     * @return el participante encontrado, o vacío si no existe
     */
    Optional<ParticipanteGrupo> findByUsuarioAndGrupo(Usuario usuario, GrupoViaje grupo);
}