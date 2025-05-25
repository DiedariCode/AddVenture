package com.add.venture.service;

import com.add.venture.dto.RegistroUsuarioDTO;

public interface UsuarioService {

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param dto el objeto DTO que contiene la información del usuario a crear
     */
    void crearUsuario(RegistroUsuarioDTO dto);
    
    /**
     * Verifica si un nombre de usuario ya existe en la base de datos.
     *
     * @param nombreUsuario el nombre de usuario a verificar
     * @return true si el nombre de usuario ya existe, false en caso contrario
     */
    boolean existeNombreUsuario(String nombreUsuario);

}

