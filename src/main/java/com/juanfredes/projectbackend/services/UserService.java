package com.juanfredes.projectbackend.services;

import com.juanfredes.projectbackend.dto.LoginRequestDto;
import com.juanfredes.projectbackend.dto.LoginResponseDto;
import com.juanfredes.projectbackend.dto.UserDto;
import com.juanfredes.projectbackend.entities.User;
import com.juanfredes.projectbackend.dto.CreateUserDto;
import com.juanfredes.projectbackend.exceptions.AppException;
import com.juanfredes.projectbackend.mappers.UserMapper;
import com.juanfredes.projectbackend.repositories.UserRepository;
import com.juanfredes.projectbackend.security.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthProvider userAuthProvider;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto saveUser(CreateUserDto createUserDto) {
        if( userRepository.existsByEmail( createUserDto.email() ) ){
            throw new AppException( "ya existe un usuario creado con ese email", HttpStatus.BAD_REQUEST );
        }

        User user = userMapper.toUser(createUserDto);
        user.setPassword( passwordEncoder.encode( user.getPassword() ) );
        User userSave  = userRepository.save( user );

        return createLoginResponse( userSave );
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User userFound = userRepository.findByEmail( loginRequestDto.email() )
                .orElseThrow( () -> new AppException("no se encontro el usuario con ese email", HttpStatus.BAD_REQUEST) );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDto.email(),
                    loginRequestDto.password()
                )
        );

        return createLoginResponse( userFound );

    }

    public LoginResponseDto checkToken(User user) {
        return createLoginResponse( user );
    }

    private LoginResponseDto createLoginResponse( User user ) {
        String token = userAuthProvider.createToken( user );

        UserDto userDto = userMapper.toUserDto( user );

        return LoginResponseDto.builder()
                .user( userDto )
                .token( token )
                .build();
    }

}
