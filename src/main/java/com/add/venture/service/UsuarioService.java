package com.add.venture.service;

import com.add.venture.dto.RegistroUsuarioDTO;

public interface UsuarioService {

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param dto el objeto DTO que contiene la información del usuario a crear
     */
    void crearUsuario(RegistroUsuarioDTO dto);

}

