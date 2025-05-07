package com.add.venture.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    private List<Viaje> viajesCreados = new ArrayList<>();
    
    // Constructor vacío
    public Usuario() {
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Viaje> getViajesCreados() {
        return viajesCreados;
    }

    public void setViajesCreados(List<Viaje> viajesCreados) {
        this.viajesCreados = viajesCreados;
    }
    
    public void addViajeCreado(Viaje viaje) {
        viajesCreados.add(viaje);
        viaje.setCreador(this);
    }
    
    public void removeViajeCreado(Viaje viaje) {
        viajesCreados.remove(viaje);
        viaje.setCreador(null);
    }
}
