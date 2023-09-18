package com.juanfredes.projectbackend.dto;

import com.juanfredes.projectbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginResponseDto {

    private UserDto user;
    private String token;

}
