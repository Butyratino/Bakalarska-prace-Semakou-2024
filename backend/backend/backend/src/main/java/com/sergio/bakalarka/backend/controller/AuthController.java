package com.sergio.bakalarka.backend.controller;

import com.sergio.bakalarka.backend.model.dto.AuthenticationRequest;
import com.sergio.bakalarka.backend.model.dto.RegistrationUserRequest;
import com.sergio.bakalarka.backend.model.dto.UserDto;
import com.sergio.bakalarka.backend.model.entity.User;
import com.sergio.bakalarka.backend.security.JwtTokenProvider;
import com.sergio.bakalarka.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @PostMapping(value = "/login")
    public ResponseEntity<UserDto> login(@RequestBody AuthenticationRequest request, HttpSession session) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();

            // Store user info in the session
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            System.out.println("Gocha");

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenProvider.generateAccessToken(user))
                    .body(user.toUserDto());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/registration")
    public void register(@RequestBody RegistrationUserRequest request) {
        userService.registration(request);
    }
}