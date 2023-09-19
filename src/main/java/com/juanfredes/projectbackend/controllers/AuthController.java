package com.juanfredes.projectbackend.controllers;

import com.juanfredes.projectbackend.dto.CreateUserDto;
import com.juanfredes.projectbackend.dto.LoginRequestDto;
import com.juanfredes.projectbackend.dto.LoginResponseDto;
import com.juanfredes.projectbackend.entities.User;
import com.juanfredes.projectbackend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> registerUser( @RequestBody @Valid CreateUserDto createUserDto ) {
        return ResponseEntity.ok( userService.saveUser( createUserDto ) );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login( @RequestBody @Valid LoginRequestDto loginRequestDto ) {
        return ResponseEntity.ok( userService.login(loginRequestDto) );
    }

    @GetMapping("/check-token")
    public ResponseEntity<LoginResponseDto> checkToken( @AuthenticationPrincipal User user ) {
        return ResponseEntity.ok( userService.checkToken( user ) );
    }
}
