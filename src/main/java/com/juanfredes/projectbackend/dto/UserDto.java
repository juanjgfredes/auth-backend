package com.juanfredes.projectbackend.dto;

import com.juanfredes.projectbackend.entities.ERol;
import com.juanfredes.projectbackend.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@Data
public class UserDto {

    private String id;
    private String name;
    private String email;
    private Boolean isActive;
    private Set<ERol> roles;
    private String token;

}
