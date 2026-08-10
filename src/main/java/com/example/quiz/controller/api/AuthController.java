package com.example.quiz.controller.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quiz.config.ApiController;
import org.springframework.web.server.ResponseStatusException;

import com.example.quiz.dto.AuthRequestDTO;
import com.example.quiz.dto.AuthResponseDTO;
import com.example.quiz.dto.RegistrationDTO;
import com.example.quiz.models.User;
import com.example.quiz.service.JwtService;
import com.example.quiz.service.UserService;

@ApiController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userService.findByUsername(request.getUsername());
        return new AuthResponseDTO(jwtService.generateToken(user));
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody RegistrationDTO request) {
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        if (!userService.saveUser(request)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this username already exists");
        }
        User user = userService.findByUsername(request.getUsername());
        return new AuthResponseDTO(jwtService.generateToken(user));
    }
}
