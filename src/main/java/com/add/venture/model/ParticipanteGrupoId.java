package com.add.venture.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteGrupoId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long usuario; // Cambiado a Long para coincidir con el tipo de Usuario.idUsuario
    private Long grupo; // Mantiene Integer para coincidir con GrupoViaje.idGrupo
}