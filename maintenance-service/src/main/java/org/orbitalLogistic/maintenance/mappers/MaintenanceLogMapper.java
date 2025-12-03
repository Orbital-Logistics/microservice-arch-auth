package org.orbitalLogistic.maintenance.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.orbitalLogistic.maintenance.dto.request.MaintenanceLogRequestDTO;
import org.orbitalLogistic.maintenance.dto.response.MaintenanceLogResponseDTO;
import org.orbitalLogistic.maintenance.entities.MaintenanceLog;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaintenanceLogMapper {

    @Mapping(target = "spacecraftName", source = "spacecraftName")
    @Mapping(target = "performedByUserName", source = "performedByUserName")
    @Mapping(target = "supervisedByUserName", source = "supervisedByUserName")
    MaintenanceLogResponseDTO toResponseDTO(
        MaintenanceLog maintenanceLog,
        String spacecraftName,
        String performedByUserName,
        String supervisedByUserName
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(request.status() != null ? request.status() : org.orbitalLogistic.maintenance.entities.enums.MaintenanceStatus.SCHEDULED)")
    MaintenanceLog toEntity(MaintenanceLogRequestDTO request);
}

