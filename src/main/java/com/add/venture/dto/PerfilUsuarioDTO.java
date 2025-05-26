package com.add.venture.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

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

    private String imagenPerfil;
    private String imagenPortada;

    private Timestamp fechaRegistro;

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

    public PerfilUsuarioDTO() {
    }

    public PerfilUsuarioDTO(String nombre, String apellido, String username, String email, String telefono, String pais,
            String ciudad, LocalDate fechaNacimiento, String biografia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.telefono = telefono;
        this.pais = pais;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.biografia = biografia;
    }

    public PerfilUsuarioDTO(String nombre, String apellido, String username, String email, String telefono, String pais,
            String ciudad, LocalDate fechaNacimiento, String biografia, String imagenPerfil, String imagenPortada) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.telefono = telefono;
        this.pais = pais;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.biografia = biografia;
        this.imagenPerfil = imagenPerfil;
        this.imagenPortada = imagenPortada;
    }

    
    public PerfilUsuarioDTO(String nombre, String apellido, String username, String email, String telefono, String pais,
            String ciudad, LocalDate fechaNacimiento, String biografia, String imagenPerfil, String imagenPortada,
            Timestamp fechaRegistro) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.telefono = telefono;
        this.pais = pais;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.biografia = biografia;
        this.imagenPerfil = imagenPerfil;
        this.imagenPortada = imagenPortada;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // Nuevo método para formato legible
    public String getFechaRegistroFormateada() {
        if (fechaRegistro == null)
            return "";

        LocalDateTime fecha = fechaRegistro.toLocalDateTime();
        String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        int anio = fecha.getYear();
        // Ejemplo: "Marzo 2023"
        return mes.substring(0, 1).toUpperCase() + mes.substring(1) + " " + anio;
    }
}
