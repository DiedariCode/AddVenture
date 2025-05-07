package com.add.venture.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DTO para la creación de un nuevo viaje
 */
public class ViajeDTO {
    
    private String titulo;
    private String destino;
    private String tipoViaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tamanoGrupo;
    private Double presupuesto;
    private String descripcion;
    private List<DiaItinerarioDTO> itinerario = new ArrayList<>();
    private Set<String> actividades = new HashSet<>();
    private String requisitos;
    private boolean notificacionesUnirse = true;
    private boolean permitirChatDirecto = true;
    
    // Constructor vacío
    public ViajeDTO() {
    }
    
    // Getters y setters
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

    public List<DiaItinerarioDTO> getItinerario() {
        return itinerario;
    }

    public void setItinerario(List<DiaItinerarioDTO> itinerario) {
        this.itinerario = itinerario;
    }

    public Set<String> getActividades() {
        return actividades;
    }

    public void setActividades(Set<String> actividades) {
        this.actividades = actividades;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public boolean isNotificacionesUnirse() {
        return notificacionesUnirse;
    }

    public void setNotificacionesUnirse(boolean notificacionesUnirse) {
        this.notificacionesUnirse = notificacionesUnirse;
    }

    public boolean isPermitirChatDirecto() {
        return permitirChatDirecto;
    }

    public void setPermitirChatDirecto(boolean permitirChatDirecto) {
        this.permitirChatDirecto = permitirChatDirecto;
    }
}
