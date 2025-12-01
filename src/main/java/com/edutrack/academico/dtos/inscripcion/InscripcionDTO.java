package com.edutrack.academico.dtos.inscripcion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {
    private Long idInscripcion;

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

    private LocalDateTime fecha;
}

