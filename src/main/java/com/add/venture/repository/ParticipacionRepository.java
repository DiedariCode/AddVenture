package com.add.venture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.add.venture.model.Participacion;

@Repository
public interface ParticipacionRepository extends JpaRepository<Participacion, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    
}
