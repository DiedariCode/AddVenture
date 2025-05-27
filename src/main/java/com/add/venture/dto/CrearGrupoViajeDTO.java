package com.add.venture.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CrearGrupoViajeDTO {

    // Información básica del viaje
    private String nombre;
    private String destino;
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer participantes;
    private Integer edadMin;
    private Integer edadMax;
    private String genero;
    private String etiquetas;
    private String descripcion;
    private MultipartFile imagen;

    // Punto de encuentro
    private String puntoEncuentro;
    private Double latitud;
    private Double longitud;

     // Itinerario (lista de días)
    private List<DiaItinerarioDTO> itinerario;

    public CrearGrupoViajeDTO() {
    }

    public CrearGrupoViajeDTO(String nombre, String destino, String tipo, LocalDate fechaInicio, LocalDate fechaFin,
                              Integer participantes, Integer edadMin, Integer edadMax, String genero,
                              String etiquetas, String descripcion, MultipartFile imagen,
                              String puntoEncuentro, Double latitud, Double longitud,
                              List<DiaItinerarioDTO> itinerario) {
        this.nombre = nombre;
        this.destino = destino;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.participantes = participantes;
        this.edadMin = edadMin;
        this.edadMax = edadMax;
        this.genero = genero;
        this.etiquetas = etiquetas;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.puntoEncuentro = puntoEncuentro;
        this.latitud = latitud;
        this.longitud = longitud;
        this.itinerario = itinerario;
    }

    
    // Constructor sin punto de encuentro, latitud, longitud e itinerario
    public CrearGrupoViajeDTO(String nombre, String destino, String tipo, LocalDate fechaInicio, LocalDate fechaFin,
            Integer participantes, Integer edadMin, Integer edadMax, String genero, String etiquetas,
            String descripcion, MultipartFile imagen) {
        this.nombre = nombre;
        this.destino = destino;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.participantes = participantes;
        this.edadMin = edadMin;
        this.edadMax = edadMax;
        this.genero = genero;
        this.etiquetas = etiquetas;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Integer participantes) {
        this.participantes = participantes;
    }

    public Integer getEdadMin() {
        return edadMin;
    }

    public void setEdadMin(Integer edadMin) {
        this.edadMin = edadMin;
    }

    public Integer getEdadMax() {
        return edadMax;
    }

    public void setEdadMax(Integer edadMax) {
        this.edadMax = edadMax;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MultipartFile getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile imagen) {
        this.imagen = imagen;
    }

    public String getPuntoEncuentro() {
        return puntoEncuentro;
    }

    public void setPuntoEncuentro(String puntoEncuentro) {
        this.puntoEncuentro = puntoEncuentro;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public List<DiaItinerarioDTO> getItinerario() {
        return itinerario;
    }

    public void setItinerario(List<DiaItinerarioDTO> itinerario) {
        this.itinerario = itinerario;
    }
    
}

