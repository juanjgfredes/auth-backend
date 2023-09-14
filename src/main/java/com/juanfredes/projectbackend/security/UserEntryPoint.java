package com.juanfredes.projectbackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanfredes.projectbackend.dto.ErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class UserEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper OBJECT_MAPPER;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ErrorDto errorDto = new ErrorDto();
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
        response.setHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

        String endpoint = request.getRequestURI();
        if( isLoginEndpoint(endpoint) ){
            errorDto.add("mensaje", "la contraseña ingresada es incorrecta");
            OBJECT_MAPPER.writeValue( response.getOutputStream(), errorDto );
            return;
        }

        String token = request.getHeader( HttpHeaders.AUTHORIZATION );
        if( istokenNullOrEmty( token ) ){
            errorDto.add( "mensaje", "no ha ingresado ningún token" );
        } else {
            errorDto.add( "mensaje", "el token ingresado es incorrecto" );
        }
        OBJECT_MAPPER.writeValue( response.getOutputStream(), errorDto );

    }

    private boolean isLoginEndpoint( String endpoint ){
        return endpoint.endsWith("/auth/login");
    }

    private boolean istokenNullOrEmty( String token ){
        return token == null || token.isEmpty();
    }
}
