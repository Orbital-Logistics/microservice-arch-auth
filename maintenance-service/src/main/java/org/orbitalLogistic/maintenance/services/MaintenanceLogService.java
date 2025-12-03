package org.orbitalLogistic.maintenance.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orbitalLogistic.maintenance.clients.SpacecraftDTO;
import org.orbitalLogistic.maintenance.clients.SpacecraftServiceClient;
import org.orbitalLogistic.maintenance.clients.UserDTO;
import org.orbitalLogistic.maintenance.clients.UserServiceClient;
import org.orbitalLogistic.maintenance.dto.common.PageResponseDTO;
import org.orbitalLogistic.maintenance.dto.request.MaintenanceLogRequestDTO;
import org.orbitalLogistic.maintenance.dto.response.MaintenanceLogResponseDTO;
import org.orbitalLogistic.maintenance.entities.MaintenanceLog;
import org.orbitalLogistic.maintenance.exceptions.InvalidOperationException;
import org.orbitalLogistic.maintenance.exceptions.MaintenanceLogNotFoundException;
import org.orbitalLogistic.maintenance.mappers.MaintenanceLogMapper;
import org.orbitalLogistic.maintenance.repositories.MaintenanceLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final MaintenanceLogMapper maintenanceLogMapper;
    private final UserServiceClient userServiceClient;
    private final SpacecraftServiceClient spacecraftServiceClient;

    public PageResponseDTO<MaintenanceLogResponseDTO> getAllMaintenanceLogs(int page, int size) {
        int offset = page * size;
        List<MaintenanceLog> logs = maintenanceLogRepository.findAllPaginated(size, offset);
        long total = maintenanceLogRepository.countAll();

        List<MaintenanceLogResponseDTO> logDTOs = logs.stream()
                .map(this::toResponseDTO)
                .toList();

        int totalPages = (int) Math.ceil((double) total / size);
        return new PageResponseDTO<>(logDTOs, page, size, total, totalPages, page == 0, page >= totalPages - 1);
    }

    public PageResponseDTO<MaintenanceLogResponseDTO> getSpacecraftMaintenanceHistory(Long spacecraftId, int page, int size) {
        int offset = page * size;
        List<MaintenanceLog> logs = maintenanceLogRepository.findBySpacecraftIdPaginated(spacecraftId, size, offset);
        long total = maintenanceLogRepository.countBySpacecraftId(spacecraftId);

        List<MaintenanceLogResponseDTO> logDTOs = logs.stream()
                .map(this::toResponseDTO)
                .toList();

        int totalPages = (int) Math.ceil((double) total / size);
        return new PageResponseDTO<>(logDTOs, page, size, total, totalPages, page == 0, page >= totalPages - 1);
    }

    public MaintenanceLogResponseDTO createMaintenanceLog(MaintenanceLogRequestDTO request) {
        try {
            Boolean spacecraftExists = spacecraftServiceClient.spacecraftExists(request.spacecraftId());
            if (spacecraftExists == null || !spacecraftExists) {
                throw new InvalidOperationException("Spacecraft not found with id: " + request.spacecraftId());
            }
        } catch (Exception e) {
            log.error("Failed to validate spacecraft: {}", e.getMessage());
            throw new InvalidOperationException("Unable to validate spacecraft. Spacecraft service may be unavailable.");
        }

        try {
            Boolean userExists = userServiceClient.userExists(request.performedByUserId());
            if (userExists == null || !userExists) {
                throw new InvalidOperationException("Performed by user not found with id: " + request.performedByUserId());
            }
        } catch (Exception e) {
            log.error("Failed to validate performed by user: {}", e.getMessage());
            throw new InvalidOperationException("Unable to validate performed by user. User service may be unavailable.");
        }

        if (request.supervisedByUserId() != null) {
            try {
                Boolean userExists = userServiceClient.userExists(request.supervisedByUserId());
                if (userExists == null || !userExists) {
                    throw new InvalidOperationException("Supervised by user not found with id: " + request.supervisedByUserId());
                }
            } catch (Exception e) {
                log.error("Failed to validate supervised by user: {}", e.getMessage());
                throw new InvalidOperationException("Unable to validate supervised by user. User service may be unavailable.");
            }
        }

        MaintenanceLog maintenanceLog = maintenanceLogMapper.toEntity(request);
        MaintenanceLog saved = maintenanceLogRepository.save(maintenanceLog);
        return toResponseDTO(saved);
    }

    public MaintenanceLogResponseDTO updateMaintenanceStatus(Long id, MaintenanceLogRequestDTO request) {
        MaintenanceLog maintenanceLog = maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new MaintenanceLogNotFoundException("Maintenance log not found with id: " + id));

        if (request.status() != null) {
            maintenanceLog.setStatus(request.status());
        }
        if (request.endTime() != null) {
            maintenanceLog.setEndTime(request.endTime());
        }
        if (request.cost() != null) {
            maintenanceLog.setCost(request.cost());
        }
        if (request.description() != null) {
            maintenanceLog.setDescription(request.description());
        }

        MaintenanceLog updated = maintenanceLogRepository.save(maintenanceLog);
        return toResponseDTO(updated);
    }

    private MaintenanceLogResponseDTO toResponseDTO(MaintenanceLog maintenanceLog) {
        String spacecraftName = "Unknown";
        String performedByUserName = "Unknown";
        String supervisedByUserName = null;

        try {
            SpacecraftDTO spacecraft = spacecraftServiceClient.getSpacecraftById(maintenanceLog.getSpacecraftId());
            if (spacecraft != null) {
                spacecraftName = spacecraft.name();
            }
        } catch (Exception e) {
            log.warn("Failed to fetch spacecraft name: {}", e.getMessage());
        }

        try {
            UserDTO user = userServiceClient.getUserById(maintenanceLog.getPerformedByUserId());
            if (user != null) {
                performedByUserName = user.username();
            }
        } catch (Exception e) {
            log.warn("Failed to fetch performed by user name: {}", e.getMessage());
        }

        if (maintenanceLog.getSupervisedByUserId() != null) {
            try {
                UserDTO user = userServiceClient.getUserById(maintenanceLog.getSupervisedByUserId());
                if (user != null) {
                    supervisedByUserName = user.username();
                }
            } catch (Exception e) {
                log.warn("Failed to fetch supervised by user name: {}", e.getMessage());
            }
        }

        return maintenanceLogMapper.toResponseDTO(maintenanceLog, spacecraftName, performedByUserName, supervisedByUserName);
    }
}

