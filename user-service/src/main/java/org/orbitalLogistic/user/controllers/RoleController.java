package org.orbitalLogistic.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orbitalLogistic.user.dto.request.RoleRequestDTO;
import org.orbitalLogistic.user.dto.response.UserResponseDTO;
import org.orbitalLogistic.user.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@Validated
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO request) {

        roleService.createRole(request.getRole());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> removeRole(@Valid @RequestBody RoleRequestDTO request) {

        roleService.removeRole(request.getRole());

        return ResponseEntity.ok().build();
    }
}
