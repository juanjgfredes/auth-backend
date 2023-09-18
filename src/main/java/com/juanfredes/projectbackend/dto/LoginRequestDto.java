package com.juanfredes.projectbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(

        @Email(message = "el email tiene un formato erroneo")
        String email,

        @NotBlank( message = "la contraseña no puede estar vacia")
        @Size( min = 6, message = "la contraseña debe tener un minimo de 6 caracteres" )
        String password
) {
}
