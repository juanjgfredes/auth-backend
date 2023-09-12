package com.juanfredes.projectbackend.entities;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User {

    @Id
    private String id;

    private String name;

    @NotBlank(message = "el email no puede estar en blanco")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "la contraseña no puede estar en blanco")
    @Size(min = 6, message = "la contraseña debe tener minimo 6 caracteres lolooo")
    private String password;

    private Boolean isActive = Boolean.TRUE;
    private ERol[] roles = {ERol.USUARIO};
}
