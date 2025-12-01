package com.edutrack.academico.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El nombre solo debe contener letras y sin espacios"
    )
    private String firstname;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El apellido solo debe contener letras y sin espacios"
    )
    private String lastname;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 64, message = "La contraseña debe tener entre 6 y 64 caracteres")
    private String password;

    private String role;
}

