package com.add.venture.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.add.venture.model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    
    /**
     * Busca viajes que no estén asignados a ningún grupo
     * 
     * @return lista de viajes no asignados a grupos
     */
    @Query("SELECT v FROM Viaje v WHERE v.grupo IS NULL")
    List<Viaje> findViajesSinGrupo();
}
