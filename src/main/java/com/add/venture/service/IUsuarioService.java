package com.add.venture.service;

import org.springframework.web.multipart.MultipartFile;

import com.add.venture.dto.PerfilUsuarioDTO;
import com.add.venture.dto.RegistroUsuarioDTO;

public interface IUsuarioService {

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


    /**
     * Busca y retorna el perfil de un usuario por su correo electrónico.
     *
     * @param email el correo electrónico del usuario cuyo perfil se busca
     * @return un objeto PerfilUsuarioDTO con la información del perfil del usuario,
     *         o null si no se encuentra
     */
    PerfilUsuarioDTO buscarPerfilPorEmail(String email);

    /**
     * Verifica si un nombre de usuario ya existe, excluyendo el usuario actual.
     *
     * @param nombreUsuario el nombre de usuario a verificar
     * @param emailActual el correo electrónico del usuario actual
     * @return true si el nombre de usuario ya existe para otro usuario, false en caso contrario
     */
    boolean existeNombreUsuarioExceptoActual(String nombreUsuario, String emailActual);


    /**
     * Actualiza el perfil de un usuario, incluyendo la subida de imágenes de perfil y portada.
     *
     * @param dto el objeto DTO que contiene la información actualizada del perfil
     * @param imagenPerfil la imagen de perfil a subir
     * @param imagenPortada la imagen de portada a subir
     */
    void actualizarPerfil(PerfilUsuarioDTO dto, MultipartFile imagenPerfil, MultipartFile imagenPortada);
}

