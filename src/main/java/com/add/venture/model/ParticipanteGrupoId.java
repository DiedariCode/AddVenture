package com.add.venture.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteGrupoId implements Serializable {
    private Integer usuario;
    private Integer grupo;
}