package com.add.venture.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellidos;

    @Column(name = "nombre_usuario", length = 30, unique = true)
    private String nombreUsuario;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 20, unique = true)
    private String telefono;

    @Column(length = 50)
    private String pais;

    @Column(length = 50)
    private String ciudad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "contraseña_hash")
    private String contrasenaHash;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @Column(name = "foto_portada")
    private String fotoPortada;

    private String descripcion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "es_verificado")
    private Boolean esVerificado = false;

    @Column(name = "estado_cuenta", length = 20)
    private String estadoCuenta = "activa";

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(length = 20)
    private String estado = "activo";

    // Relaciones
    @ManyToMany
    @JoinTable(name = "UsuarioEtiqueta", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_etiqueta"))
    private Set<Etiqueta> etiquetas;

    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    private Set<GrupoViaje> gruposCreados;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<ParticipanteGrupo> participaciones;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private Set<Resena> resenasEscritas;

    @OneToMany(mappedBy = "destinatario", cascade = CascadeType.ALL)
    private Set<Resena> resenasRecibidas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Notificacion> notificaciones;

    @ManyToMany
    @JoinTable(name = "UsuarioLogro", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_logro"))
    private Set<Logro> logros;
}
