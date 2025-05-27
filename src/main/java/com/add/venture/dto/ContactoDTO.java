package com.add.venture.dto;

public class ContactoDTO {
    private String nombre;
    private String email;
    private String asunto;
    private String categoria;
    private String mensaje;

    // Constructor por defecto
    public ContactoDTO() {
    }

    // Constructor con parámetros
    public ContactoDTO(String nombre, String email, String asunto, String categoria, String mensaje) {
        this.nombre = nombre;
        this.email = email;
        this.asunto = asunto;
        this.categoria = categoria;
        this.mensaje = mensaje;
    }

    // Getters y Setters
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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
