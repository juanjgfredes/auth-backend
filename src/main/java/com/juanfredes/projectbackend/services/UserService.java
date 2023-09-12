package com.juanfredes.projectbackend.services;

import com.juanfredes.projectbackend.dto.LoginDto;
import com.juanfredes.projectbackend.dto.UserDto;
import com.juanfredes.projectbackend.entities.User;
import com.juanfredes.projectbackend.dto.CreateUserDto;
import com.juanfredes.projectbackend.exceptions.AppException;
import com.juanfredes.projectbackend.mappers.UserMapper;
import com.juanfredes.projectbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(CreateUserDto createUserDto) {
        User user = userMapper.toUser(createUserDto);
        user.setPassword( passwordEncoder.encode( user.getPassword() ) );

        return userRepository.save(user);
    }

    public UserDto login(LoginDto loginDto) {
        User user = User.builder()
                .email( loginDto.email() )
                .build();

        User userFound = userRepository.findOne( Example.of( user ) )
                .orElseThrow( () -> new AppException("no se encontro el usuario con ese email", HttpStatus.BAD_REQUEST) );

        if( !passwordEncoder.matches( loginDto.password() ,userFound.getPassword() ) ) throw new AppException("la contraseña es incorrecta", HttpStatus.BAD_REQUEST );

        return userMapper.toUserDto( userFound );
    }
}
