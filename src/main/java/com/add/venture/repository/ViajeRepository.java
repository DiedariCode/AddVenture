package com.add.venture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.add.venture.model.Viaje;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    public List<Viaje> findByCategoria(String categoria);
    public List<Viaje> findByParticipantesUsuarioId(Long idUsuario); 
}
