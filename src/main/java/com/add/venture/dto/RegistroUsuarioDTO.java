package com.add.venture.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String nombreUsuario;

    @Email
    private String email;

    @NotBlank
    private String telefono;

    @NotBlank
    private String pais;

    @NotBlank
    private String ciudad;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotBlank
    private String contrasena;

    @NotBlank
    private String confirmContrasena;

    public String getIniciales() {
        StringBuilder iniciales = new StringBuilder();

        if (nombre != null && !nombre.isBlank()) {
            iniciales.append(Character.toUpperCase(nombre.trim().charAt(0)));
        }

        if (apellido != null && !apellido.isBlank()) {
            iniciales.append(Character.toUpperCase(apellido.trim().charAt(0)));
        }

        return iniciales.toString();
    }
}
