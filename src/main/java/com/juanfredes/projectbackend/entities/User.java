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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User implements UserDetails {

    @Id
    private String id;

    private String name;

    @NotBlank(message = "el email no puede estar en blanco")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "la contraseña no puede estar en blanco")
    @Size(min = 6, message = "la contraseña debe tener minimo 6 caracteres lolooo")
    private String password;

    @Builder.Default
    private Boolean isActive = Boolean.TRUE;
    @Builder.Default
    private Set<ERol> roles = Set.of(ERol.USUARIO);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map( rol -> rol.getAuthority() ).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
