package com.add.venture.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaItinerarioDTO {
    
    @NotNull(message = "El número de día es obligatorio")
    private Integer diaNumero;
    
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    private String descripcion;

    private String puntoPartida;
    
    private String puntoLlegada;
    
    private String duracionEstimada;
}
