package org.orbitalLogistic.spacecraft.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orbitalLogistic.spacecraft.dto.common.PageResponseDTO;
import org.orbitalLogistic.spacecraft.dto.request.SpacecraftRequestDTO;
import org.orbitalLogistic.spacecraft.dto.response.SpacecraftResponseDTO;
import org.orbitalLogistic.spacecraft.entities.enums.SpacecraftStatus;
import org.orbitalLogistic.spacecraft.services.SpacecraftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spacecrafts")
@RequiredArgsConstructor
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_COMMANDER')")
    public ResponseEntity<PageResponseDTO<SpacecraftResponseDTO>> getAllSpacecrafts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        if (size > 50) {
            size = 50;
        }

        PageResponseDTO<SpacecraftResponseDTO> response = spacecraftService.getSpacecrafts(name, status, page, size);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(response.totalElements()))
                .body(response);
    }

    @GetMapping("/scroll")
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_COMMANDER')")
    public ResponseEntity<List<SpacecraftResponseDTO>> getSpacecraftsScroll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        if (size > 50) {
            size = 50;
        }

        List<SpacecraftResponseDTO> response = spacecraftService.getSpacecraftsScroll(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_COMMANDER')")
    public ResponseEntity<SpacecraftResponseDTO> getSpacecraftById(@PathVariable Long id) {
        SpacecraftResponseDTO response = spacecraftService.getSpacecraftById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_COMMANDER')")
    public ResponseEntity<SpacecraftResponseDTO> createSpacecraft(@Valid @RequestBody SpacecraftRequestDTO request) {
        SpacecraftResponseDTO response = spacecraftService.createSpacecraft(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_COMMANDER')")
    public ResponseEntity<SpacecraftResponseDTO> updateSpacecraft(
            @PathVariable Long id,
            @Valid @RequestBody SpacecraftRequestDTO request) {

        SpacecraftResponseDTO response = spacecraftService.updateSpacecraft(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<List<SpacecraftResponseDTO>> getAvailableSpacecrafts() {
        List<SpacecraftResponseDTO> response = spacecraftService.getAvailableSpacecrafts();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<SpacecraftResponseDTO> updateSpacecraftStatus(
            @PathVariable Long id,
            @RequestParam SpacecraftStatus status) {

        SpacecraftResponseDTO response = spacecraftService.updateSpacecraftStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/exists")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAINTENANCE_ENGINEER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<Boolean> spacecraftExists(@PathVariable Long id) {
        boolean exists = spacecraftService.spacecraftExists(id);
        return ResponseEntity.ok(exists);
    }
}
