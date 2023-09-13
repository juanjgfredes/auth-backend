package com.juanfredes.projectbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.juanfredes.projectbackend.dto.UserDto;
import com.juanfredes.projectbackend.entities.ERol;
import com.juanfredes.projectbackend.entities.User;
import com.juanfredes.projectbackend.exceptions.AppException;
import com.juanfredes.projectbackend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    private final UserRepository userRepository;

    @PostConstruct
    public void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken( User user ) {
        Date now = new Date();
        Date validity = new Date( now.getTime() + 10_800_000 ); //Expira en 3hs
        String[] arrayRoles = user.getRoles().stream()
                .map( Enum::name )
                .toArray( String[]::new );

        return JWT.create()
                .withIssuer( user.getEmail() )
                .withIssuedAt( now )
                .withExpiresAt( validity )
                .withClaim("name", user.getName())
                .withClaim("id", user.getId())
                .withArrayClaim("roles", arrayRoles)
                .sign(Algorithm.HMAC256(secretKey));

    }

    public Authentication validityToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        Set<ERol> rolesEnums = Arrays.stream(decoded.getClaim( "roles" ).asArray( String.class ))
                .map( rol -> ERol.valueOf(rol))
                .collect( Collectors.toSet() );

        User user = User.builder()
                .email( decoded.getSubject() )
                .name( decoded.getClaim("name").asString() )
                .id( decoded.getClaim("id").asString() )
                .roles( rolesEnums )
                .build();

        User userCoincidence = userRepository.findOne( Example.of( user ) )
                .orElseThrow( () -> new AppException( "el token es incorrecto", HttpStatus.BAD_REQUEST ) );

        return new UsernamePasswordAuthenticationToken( userCoincidence, null, userCoincidence.getAuthorities() );
    }



}
