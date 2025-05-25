package com.add.venture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.add.venture.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar un usuario por su nombre de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    // Método para buscar un usuario por su email
    Optional<Usuario> findByEmail(String email);

    // Método para verificar si un nombre de usuario ya existe
    boolean existsByNombreUsuario(String nombreUsuario);

    // Método para verificar si un email ya existe
    boolean existsByEmail(String email);

}
