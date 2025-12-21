package org.orbitalLogistic.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orbitalLogistic.user.dto.request.LogInRequestDTO;
import org.orbitalLogistic.user.dto.request.SignUpRequestDTO;
import org.orbitalLogistic.user.dto.response.JwtAuthResponse;
import org.orbitalLogistic.user.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody SignUpRequestDTO request) {
        String token = authService.signUp(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getRoles()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new JwtAuthResponse(token));
    }

    @PostMapping("/log-in")
    public ResponseEntity<JwtAuthResponse> logIn(@Valid @RequestBody LogInRequestDTO request) {
        String token = authService.logIn(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity
                .ok()
                .body(new JwtAuthResponse(token));
    }
}
