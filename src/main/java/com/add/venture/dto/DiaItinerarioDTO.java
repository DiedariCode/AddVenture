package com.add.venture.dto;

import java.time.LocalDate;


public class DiaItinerarioDTO {
    private int numeroDia;
    private LocalDate fecha;
    private String titulo;
    private String descripcion;

    public DiaItinerarioDTO(int numeroDia, LocalDate fecha, String titulo, String descripcion) {
        this.numeroDia = numeroDia;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public DiaItinerarioDTO() {
    }

    // Getters y Setters
    public int getNumeroDia() {
        return numeroDia;
    }

    public void setNumeroDia(int numeroDia) {
        this.numeroDia = numeroDia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
