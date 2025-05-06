package com.add.venture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.add.venture.model.Viaje;
import com.add.venture.repository.ViajeRepository;

public class ViajeServiceImpl implements ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Override
    public Viaje actualizarViaje(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    @Override
    public Viaje buscarViajePorId(Long id) {
        return viajeRepository.findById(id).get();
    }

    @Override
    public void eliminarViajePorId(Long id) {
        viajeRepository.deleteById(id); 
    }

    @Override
    public Viaje guardarViaje(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    @Override
    public List<Viaje> listarViajes() {
        return viajeRepository.findAll();
    }

    @Override
    public List<Viaje> listarViajesPorCategoria(String categoria) {
        return viajeRepository.findByCategoria(categoria);
    }

    @Override
    public List<Viaje> listarViajesPorUsuario(Long idUsuario) {
        return viajeRepository.findByParticipantesUsuarioId(idUsuario);
    }

    
}
