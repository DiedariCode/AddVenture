package com.add.venture.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.add.venture.model.Viaje;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    /**
     * Encuentra viajes por destino
     */
    List<Viaje> findByDestinoContainingIgnoreCase(String destino);

    /**
     * Encuentra viajes por tipo
     */
    List<Viaje> findByTipoViaje(String tipoViaje);

    /**
     * Encuentra viajes por rango de fechas
     */
    @Query("SELECT v FROM Viaje v WHERE v.fechaInicio >= :fechaDesde AND v.fechaFin <= :fechaHasta")
    List<Viaje> findByRangoFechas(@Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta);

    /**
     * Encuentra viajes por rango de presupuesto
     */
    @Query("SELECT v FROM Viaje v WHERE v.presupuesto BETWEEN :minPresupuesto AND :maxPresupuesto")
    List<Viaje> findByRangoPresupuesto(@Param("minPresupuesto") Double minPresupuesto,
            @Param("maxPresupuesto") Double maxPresupuesto);

    /**
     * Encuentra viajes creados por un usuario específico
     */
    List<Viaje> findByCreadorId(Long creadorId);
}
