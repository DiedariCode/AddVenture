package com.add.venture.service;

import java.util.List;

import com.add.venture.dto.ViajeDTO;
import com.add.venture.dto.ViajeRespuestaDTO;
import com.add.venture.model.Viaje;

public interface ViajeService {

    Viaje crearViaje(ViajeDTO viajeDTO);
    
}
