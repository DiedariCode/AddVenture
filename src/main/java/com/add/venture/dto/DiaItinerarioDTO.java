package com.add.venture.dto;

import java.time.LocalDate;

/**
 * DTO para el día de itinerario
 */
public class DiaItinerarioDTO {
    private Integer numeroDia;
    private String descripcion;

    // Constructor vacío
    public DiaItinerarioDTO() {
    }

    // Constructor con parámetros
    public DiaItinerarioDTO(Integer numeroDia, String descripcion) {
        this.numeroDia = numeroDia;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public Integer getNumeroDia() {
        return numeroDia;
    }

    public void setNumeroDia(Integer numeroDia) {
        this.numeroDia = numeroDia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

