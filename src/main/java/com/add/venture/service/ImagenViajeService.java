package com.add.venture.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface ImagenViajeService {
    /**
     * Sube imágenes para un viaje
     *
     * @param viajeId ID del viaje
     * @param imagenes Lista de archivos de imagen
     * @return Lista de URLs de las imágenes subidas
     */
    List<String> guardarImagenes(Long viajeId, List<MultipartFile> imagenes);

    /**
     * Elimina una imagen de un viaje
     *
     * @param viajeId ID del viaje
     * @param imagenId ID de la imagen a eliminar
     */
    void eliminarImagen(Long viajeId, Long imagenId);
    
}
