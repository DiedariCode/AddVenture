package com.add.venture.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UsuarioLogro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UsuarioLogroId.class)
public class UsuarioLogro {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_logro")
    private Logro logro;

    @Column(name = "fecha_otorgado")
    private LocalDate fechaOtorgado;
}
