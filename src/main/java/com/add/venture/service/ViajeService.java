package com.add.venture.service;

import java.util.List;

import com.add.venture.model.Viaje;

public interface ViajeService {
    
    public Viaje guardarViaje(Viaje viaje);

    public Viaje buscarViajePorId(Long id);

    public Viaje actualizarViaje(Viaje viaje);

    public void eliminarViajePorId(Long id);

    public List<Viaje> listarViajes();

    public List<Viaje> listarViajesPorUsuario(Long idUsuario);

    public List<Viaje> listarViajesPorCategoria(String categoria);
}
