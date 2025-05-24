package com.add.venture.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoViaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_creador", nullable = false)
    @NotNull(message = "El creador del grupo es obligatorio")
    private Usuario idCreador;

    @NotBlank(message = "El nombre del viaje no puede estar en blanco")
    private String nombreViaje;

    @NotBlank(message = "El destino principal es obligatorio")
    private String destinoPrincipal;

    @NotBlank(message = "El tipo de viaje es obligatorio")
    private String tipoViaje;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @Min(value = 18, message = "La edad mínima debe ser mayor o igual a 18")
    private int rangoEdadMin;

    @Max(value = 35, message = "La edad máxima debe ser al menos 35")
    private int rangoEdadMax;

    @NotBlank(message = "El tipo de grupo es obligatorio")
    private String tipoGrupo;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String descripcion;

    @NotBlank(message = "El punto de encuentro es obligatorio")
    private String puntoEncuentro;

    private String imagenDestacada;

    private Boolean esVerificado;
}
