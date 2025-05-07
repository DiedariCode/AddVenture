package com.add.venture.dto;

import java.time.LocalDate;

/**
 * DTO para la respuesta tras crear un viaje
 */
public class ViajeRespuestaDTO {

    private Long id;
    private String titulo;
    private String destino;
    private String tipoViaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tamanoGrupo;
    private Double presupuesto;
    private String descripcion;
    private String creadorNombre;

    // Constructor vacío
    public ViajeRespuestaDTO() {
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipoViaje() {
        return tipoViaje;
    }

    public void setTipoViaje(String tipoViaje) {
        this.tipoViaje = tipoViaje;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTamanoGrupo() {
        return tamanoGrupo;
    }

    public void setTamanoGrupo(String tamanoGrupo) {
        this.tamanoGrupo = tamanoGrupo;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreadorNombre() {
        return creadorNombre;
    }

    public void setCreadorNombre(String creadorNombre) {
        this.creadorNombre = creadorNombre;
    }
}
