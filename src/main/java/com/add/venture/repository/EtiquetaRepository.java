package com.add.venture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.add.venture.model.Etiqueta;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Integer> {
    
    /**
     * Busca una etiqueta por su nombre
     * 
     * @param nombreEtiqueta el nombre de la etiqueta a buscar
     * @return la etiqueta encontrada, o vacío si no existe
     */
    Optional<Etiqueta> findByNombreEtiqueta(String nombreEtiqueta);
}