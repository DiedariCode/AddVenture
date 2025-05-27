package com.add.venture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.add.venture.model.TipoViaje;

public interface TipoViajeRepository extends JpaRepository<TipoViaje, Long> {
    
}