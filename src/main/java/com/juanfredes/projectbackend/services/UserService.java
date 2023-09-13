package com.juanfredes.projectbackend.services;

import com.juanfredes.projectbackend.dto.LoginDto;
import com.juanfredes.projectbackend.dto.UserDto;
import com.juanfredes.projectbackend.entities.User;
import com.juanfredes.projectbackend.dto.CreateUserDto;
import com.juanfredes.projectbackend.exceptions.AppException;
import com.juanfredes.projectbackend.mappers.UserMapper;
import com.juanfredes.projectbackend.repositories.UserRepository;
import com.juanfredes.projectbackend.security.UserAuthProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    public UserDto saveUser(CreateUserDto createUserDto) {
        if( userRepository.existUserByEmail( createUserDto.email() ) ){
            throw new AppException( "ya existe un usuario creado con ese email", HttpStatus.BAD_REQUEST );
        }

        User user = userMapper.toUser(createUserDto);
        user.setPassword( passwordEncoder.encode( user.getPassword() ) );
        User userSave  = userRepository.save( user );

        return createUserDtoWithToken( userSave );
    }

    public UserDto login(LoginDto loginDto) {
        User userFound = userRepository.findByEmail( loginDto.email() )
                .orElseThrow( () -> new AppException("no se encontro el usuario con ese email", HttpStatus.BAD_REQUEST) );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.email(),
                    loginDto.password()
                )
        );

        return createUserDtoWithToken( userFound );
    }

    private UserDto createUserDtoWithToken( User user ) {
        String token = userAuthProvider.createToken( user );

        UserDto userDto = userMapper.toUserDto( user );
        userDto.setToken( token );

        return userDto;
    }
}
