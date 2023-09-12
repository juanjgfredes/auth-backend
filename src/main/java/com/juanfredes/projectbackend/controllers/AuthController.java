package com.juanfredes.projectbackend.controllers;

import com.juanfredes.projectbackend.dto.CreateUserDto;
import com.juanfredes.projectbackend.dto.LoginDto;
import com.juanfredes.projectbackend.dto.UserDto;
import com.juanfredes.projectbackend.entities.User;
import com.juanfredes.projectbackend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser( @RequestBody @Valid CreateUserDto createUserDto ) {
        return ResponseEntity.ok( userService.saveUser( createUserDto ) );
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> responseEntity(@RequestBody @Valid LoginDto loginDto ) {
        return ResponseEntity.ok( userService.login( loginDto ) );
    }
}
