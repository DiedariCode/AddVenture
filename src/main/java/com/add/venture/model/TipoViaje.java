package com.add.venture.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TipoViaje")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoViaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Long idTipo;

    @Column(name = "nombre_tipo", length = 50, unique = true)
    private String nombreTipo;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL)
    private Set<Viaje> viajes;
}
