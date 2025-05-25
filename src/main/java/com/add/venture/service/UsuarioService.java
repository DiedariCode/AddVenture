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


    /**
     * Verifica si un email ya existe en la base de datos.
     *
     * @param email el email a verificar
     * @return true si el email ya existe, false en caso contrario
     */
    boolean existeEmail(String email);


    /**
     * Busca y retorna un usuario por su correo electrónico.
     *
     * @param email el correo electrónico del usuario a buscar
     * @return un objeto con la información del usuario, o null si no se encuentra
     */
    RegistroUsuarioDTO buscarPorEmail(String email);
}

