package com.add.venture.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.add.venture.model.ImagenViaje;

@Repository
public interface ImagenViajeRepository extends JpaRepository<ImagenViaje, Long> {
    /**
     * Busca una imagen por su ID
     */
    Optional<ImagenViaje> findById(Long id);

    /**
     * Busca todas las imágenes asociadas a un viaje
     */
    List<ImagenViaje> findByViajeId(Long viajeId);
    
}
