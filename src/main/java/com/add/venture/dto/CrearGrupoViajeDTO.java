package com.add.venture.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearGrupoViajeDTO {

    // Datos del grupo
    @NotBlank(message = "El nombre del viaje es obligatorio")
    @Size(max = 100, message = "El nombre del viaje no puede exceder los 100 caracteres")
    private String nombreViaje;
    
    @NotBlank(message = "El tipo de grupo es obligatorio")
    @Size(max = 50, message = "El tipo de grupo no puede exceder los 50 caracteres")
    private String tipoGrupo;
    
    // Datos del viaje
    @NotBlank(message = "El destino principal es obligatorio")
    @Size(max = 100, message = "El destino principal no puede exceder los 100 caracteres")
    private String destinoPrincipal;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotBlank(message = "El punto de encuentro es obligatorio")
    private String puntoEncuentro;
    
    private String imagenDestacada;
    
    private Integer rangoEdadMin;
    
    private Integer rangoEdadMax;
    
    private Long idTipoViaje;
    
    // Nuevos campos
    private Integer maxParticipantes;
    private List<String> etiquetas;
    
    // Para almacenar el JSON del itinerario
    private String diasItinerarioJson;
    
    // Para itinerario (se usará en el servicio)
    private List<DiaItinerarioDTO> diasItinerario;
}
