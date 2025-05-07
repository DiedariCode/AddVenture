package com.add.venture.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "viajes")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)
    private String titulo;
    
    @Column(nullable = true)
    private String destino;

    @Column(nullable = true)
    private String tipoViaje;
    
    @Column(nullable = true)
    private LocalDate fechaInicio;
    
    @Column(nullable = true)
    private LocalDate fechaFin;
    
    @Column(nullable = true)
    private String tamanoGrupo;
    
    @Column(nullable = true)
    private Double presupuesto;
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String descripcion;
    
    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiasItinerario> itinerario = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "viaje_actividades",
        joinColumns = @JoinColumn(name = "viaje_id"),
        inverseJoinColumns = @JoinColumn(name = "actividad_id")
    )
    private Set<Actividad> actividades = new HashSet<>(); // Con el set no se permite duplicados
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String requisitos;
    
    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenViaje> imagenes = new ArrayList<>();
    
    @Column(nullable = true)
    private boolean notificacionesUnirse = true;
    
    @Column(nullable = true)
    private boolean permitirChatDirecto = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario creador;
    
    // Constructor vacío
    public Viaje() {
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

    public List<DiasItinerario> getItinerario() {
        return itinerario;
    }

    public void setItinerario(List<DiasItinerario> itinerario) {
        this.itinerario = itinerario;
    }
    
    public void addDiaItinerario(DiasItinerario dia) {
        itinerario.add(dia);
        dia.setViaje(this);
    }
    
    public void removeDiaItinerario(DiasItinerario dia) {
        itinerario.remove(dia);
        dia.setViaje(null);
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public List<ImagenViaje> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenViaje> imagenes) {
        this.imagenes = imagenes;
    }
    
    public void addImagen(ImagenViaje imagen) {
        imagenes.add(imagen);
        imagen.setViaje(this);
    }
    
    public void removeImagen(ImagenViaje imagen) {
        imagenes.remove(imagen);
        imagen.setViaje(null);
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

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }
}