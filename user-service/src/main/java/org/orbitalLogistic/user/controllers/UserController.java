package org.orbitalLogistic.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.orbitalLogistic.user.dto.response.UserResponseDTO;
import org.orbitalLogistic.user.entities.Role;
import org.orbitalLogistic.user.entities.User;
import org.orbitalLogistic.user.exceptions.common.BadRequestException;
import org.orbitalLogistic.user.exceptions.update.EmptyUpdateRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.orbitalLogistic.user.dto.request.UpdateUserRequestDTO;
import org.orbitalLogistic.user.services.UserService;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserRequestDTO request) {

        if (request.getUsername() == null) {
            throw new BadRequestException("Username is required");
        }

        if (request.getPassword() == null && request.getEmail() == null && request.getRoles() == null) {
            throw new EmptyUpdateRequestException("Update request should contains new user parameters");
        }

        User user = userService.updateUser(request.getUsername(), request.getPassword(), request.getEmail(), request.getRoles());

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();

        return ResponseEntity
                .ok()
                .body(userResponseDTO);
    }

    @PostMapping("/remove-roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> removeRoles(@Valid @RequestBody UpdateUserRequestDTO request) {
        if (request.getRoles() == null) {
            throw new EmptyUpdateRequestException("Request should contains roles");
        }

        User user = userService.removeRoles(request.getUsername(), request.getRoles());

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();

        return ResponseEntity
                .ok()
                .body(userResponseDTO);
    }

//    @PostMapping("/register")
//    public Mono<ResponseEntity<UserResponseDTO>> registerUser(@Valid @RequestBody SignUpRequestDTO request) {
//        return userService.registerUser(request)
//                .map(response ->
//                        ResponseEntity
//                                .status(HttpStatus.CREATED)
//                                .body(response));
//    }

//    @GetMapping("/email/{email}")
//    public Mono<ResponseEntity<UserResponseDTO>> getUserByEmail(@PathVariable String email) {
//        return userService.findUserByEmail(email)
//                .map(response ->
//                        ResponseEntity
//                                .ok()
//                                .body(response));
//    }

//    @GetMapping("/{id}")
//    public Mono<ResponseEntity<UserResponseDTO>> getUserById(@PathVariable Long id) {
//        return userService.findUserById(id)
//                .map(response ->
//                        ResponseEntity
//                                .ok()
//                                .body(response));
//    }

//    @GetMapping("/{id}/username")
//    public Mono<ResponseEntity<String>> getUsernameById(@PathVariable Long id) {
//        return userService.findUserById(id)
//                .map(response ->
//                        ResponseEntity
//                                .ok()
//                                .body(response.username()));
//    }

//    @PutMapping("/{id}")
//    public Mono<ResponseEntity<UserResponseDTO>> updateUser(
//            @PathVariable Long id,
//            @Valid @RequestBody UpdateUserRequestDTO request
//    ) {
//        return userService.updateUser(id, request)
//                .map(response ->
//                        ResponseEntity
//                                .ok()
//                                .body(response));
//    }

//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
//        return userService.deleteUser(id).thenReturn(ResponseEntity.noContent().build());
//    }

//    @GetMapping("/{id}/exists")
//    public Mono<ResponseEntity<Boolean>> userExists(@PathVariable Long id) {
//        return userService.userExists(id)
//                .map(ResponseEntity::ok);
//    }
}
