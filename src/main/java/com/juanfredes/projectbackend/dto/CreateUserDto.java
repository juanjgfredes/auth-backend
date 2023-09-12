package com.juanfredes.projectbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank(message = "el nombre no puede estar vacio")
        String name,
        @Email(message = "tiene que tener formato de email")
        String email,
        @Size(min = 6, message = "la contraseña debe tener minimo 6 caracteres l¡vobo")
        String password
) {
}
