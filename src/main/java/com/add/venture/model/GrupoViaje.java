package com.add.venture.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GrupoViaje")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoViaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer idGrupo;

    @Column(name = "nombre_viaje", length = 100)
    private String nombreViaje;

    @Column(name = "tipo_grupo", length = 50)
    private String tipoGrupo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(length = 20)
    private String estado = "activo";

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_creador")
    private Usuario creador;

    @OneToOne
    @JoinColumn(name = "id_viaje", unique = true)
    private Viaje viaje;

    @ManyToMany
    @JoinTable(name = "GrupoEtiqueta", joinColumns = @JoinColumn(name = "id_grupo"), inverseJoinColumns = @JoinColumn(name = "id_etiqueta"))
    private Set<Etiqueta> etiquetas;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<Itinerario> itinerarios;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<ParticipanteGrupo> participantes;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<MensajeGrupo> mensajes;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<Resena> resenas;
}
