package org.orbitalLogistic.maintenance.dto.response;

import org.orbitalLogistic.maintenance.entities.enums.MaintenanceType;
import org.orbitalLogistic.maintenance.entities.enums.MaintenanceStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaintenanceLogResponseDTO(
    Long id,
    Long spacecraftId,
    String spacecraftName,
    MaintenanceType maintenanceType,
    Long performedByUserId,
    String performedByUserName,
    Long supervisedByUserId,
    String supervisedByUserName,
    LocalDateTime startTime,
    LocalDateTime endTime,
    MaintenanceStatus status,
    String description,
    BigDecimal cost
) {}

