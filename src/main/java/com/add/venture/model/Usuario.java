package com.add.venture.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 DIGITOS ANIMAL")
    private String telefono;

    private String pais;

    private String ciudad;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasenaHash;

    private String fotoPerfil;

    private String fotoPortada;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String descripcion;

    private Timestamp fechaRegistro;

    private boolean esVerificado;

    @DecimalMin(value = "0.0", message = "La puntuación no puede ser negativa")
    @DecimalMax(value = "5.0", message = "La puntuación máxima es 5.0")
    private BigDecimal puntosReputacion;

    private int resenasPositivas;

    @NotBlank(message = "El estado de la cuenta es obligatorio")
    private String estadoCuenta; // ACTIVA, ELIMINADA, SUSPENDIDA

    @OneToMany(mappedBy = "idCreador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrupoViaje> gruposCreados;
}
