package com.edutrack.academico.dtos.calificacion;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionDTO {
    private Long idCalificacion;

    @NotNull(message = "El estudiante es obligatorio")
    @Positive(message = "El id de estudiante debe ser positivo")
    private Long estudianteId;

    // Nombre completo del estudiante para mostrar en tablas
    private String estudianteNombre;

    @NotNull(message = "El curso es obligatorio")
    @Positive(message = "El id de curso debe ser positivo")
    private Long cursoId;

    // Nombre del curso para mostrar en tablas
    private String cursoNombre;

    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "0.0", message = "La nota no puede ser menor a 0")
    @DecimalMax(value = "100.0", message = "La nota no puede ser mayor a 100")
    @Digits(integer = 3, fraction = 2, message = "La nota debe tener como máximo 2 decimales")
    private BigDecimal notaFinal;
}

