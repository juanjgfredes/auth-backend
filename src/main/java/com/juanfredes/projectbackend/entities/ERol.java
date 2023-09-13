package com.juanfredes.projectbackend.entities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ERol {
    ADMIN,
    USUARIO,
    INVITADO;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority( "ROLE_".concat( this.name() ) );
    }
}
