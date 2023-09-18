package com.juanfredes.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.juanfredes.projectbackend.entities.ERol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

}
