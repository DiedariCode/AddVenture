package com.add.venture.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.add.venture.model.ImagenViaje;
import com.add.venture.model.Viaje;
import com.add.venture.repository.ImagenViajeRepository;
import com.add.venture.repository.ViajeRepository;

import jakarta.persistence.criteria.Path;

@Service
public class ImagenViajeServiceImpl implements ImagenViajeService {

    private static final String UPLOAD_DIR = "uploads/viajes";
    @Autowired
    private ImagenViajeRepository imagenViajeRepository;
    @Autowired
    private ViajeRepository viajeRepository;

    @Override
    public List<String> guardarImagenes(Long viajeId, List<MultipartFile> imagenes) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + viajeId));

        List<String> rutasGuardadas = new ArrayList<>();

        for (MultipartFile archivo : imagenes) {
            if (!archivo.isEmpty()) {
                try {
                    String nombreUnico = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                    java.nio.file.Path destino = Paths.get(UPLOAD_DIR).resolve(nombreUnico);
                    Files.createDirectories(destino.getParent());
                    Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                    ImagenViaje imagen = new ImagenViaje();
                    imagen.setNombreArchivo(nombreUnico);
                    imagen.setRuta(destino.toString());
                    imagen.setViaje(viaje);
                    imagenViajeRepository.save(imagen);

                    rutasGuardadas.add(destino.toString()); // Puedes modificar esto para devolver URLs si lo necesitas
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar la imagen: " + archivo.getOriginalFilename(), e);
                }
            }
        }
        return rutasGuardadas;
    }

    @Override
    public void eliminarImagen(Long viajeId, Long imagenId) {
        // TODO Auto-generated method stub

    }

}
