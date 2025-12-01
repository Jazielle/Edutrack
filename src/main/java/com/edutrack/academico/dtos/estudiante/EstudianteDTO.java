package com.edutrack.academico.dtos.estudiante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private Long idEstudiante;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El nombre solo debe contener letras y sin espacios"
    )
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El apellido solo debe contener letras y sin espacios"
    )
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    private String estado;
}

