package com.add.venture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.add.venture.model.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    /**
     * Busca una actividad por su nombre
     */
    Optional<Actividad> findByNombre(String nombre);
}
