package com.juanfredes.projectbackend.dto;

import com.juanfredes.projectbackend.entities.ERol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@Data
public class UserDto {

    private String id;
    private String name;
    private String email;
    private Boolean isActive;
    private ERol[] roles;
    private String token;

}
