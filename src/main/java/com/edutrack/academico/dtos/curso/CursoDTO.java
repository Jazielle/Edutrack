package com.edutrack.academico.dtos.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long idCurso;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\- ]+$",
            message = "El nombre del curso solo debe contener letras, números, espacios y guiones"
    )
    private String nombre;

    @NotBlank(message = "El nombre del profesor es obligatorio")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El nombre del profesor solo debe contener letras y sin espacios"
    )
    private String profesor;
}

