package com.add.venture.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilUsuarioDTO {
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String telefono;
    private String pais;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String biografia;

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

    public Integer getEdad() {
        if (fechaNacimiento == null) {
            return null;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear() -
                ((LocalDate.now().getDayOfYear() < fechaNacimiento.getDayOfYear()) ? 1 : 0);
    }
}
