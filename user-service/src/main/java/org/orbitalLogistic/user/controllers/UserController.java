package org.orbitalLogistic.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.orbitalLogistic.user.dto.request.UpdateUserRequestDTO;
import org.orbitalLogistic.user.dto.request.UserRegistrationRequestDTO;
import org.orbitalLogistic.user.dto.response.UserResponseDTO;
import org.orbitalLogistic.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Mono<ResponseEntity<UserResponseDTO>> registerUser(@Valid @RequestBody UserRegistrationRequestDTO request) {
        return userService.registerUser(request)
                .map(response ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response));
    }

    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<UserResponseDTO>> getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email)
                .map(response ->
                        ResponseEntity
                                .ok()
                                .body(response));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDTO>> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(response ->
                        ResponseEntity
                                .ok()
                                .body(response));
    }

    @GetMapping("/{id}/username")
    public Mono<ResponseEntity<String>> getUsernameById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(response ->
                        ResponseEntity
                                .ok()
                                .body(response.username()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDTO request
    ) {
        return userService.updateUser(id, request)
                .map(response ->
                        ResponseEntity
                                .ok()
                                .body(response));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id).thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}/exists")
    public Mono<ResponseEntity<Boolean>> userExists(@PathVariable Long id) {
        return userService.userExists(id)
                .map(ResponseEntity::ok);
    }
}
