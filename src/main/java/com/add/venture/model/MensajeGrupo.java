package com.add.venture.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MensajeGrupo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long idMensaje;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();

    @Column(length = 20)
    private String estado = "activo";

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private GrupoViaje grupo;

    @ManyToOne
    @JoinColumn(name = "id_remitente")
    private Usuario remitente;
}