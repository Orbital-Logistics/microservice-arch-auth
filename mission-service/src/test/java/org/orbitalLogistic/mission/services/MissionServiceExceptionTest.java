package org.orbitalLogistic.mission.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.mission.clients.resilient.ResilientSpacecraftService;
import org.orbitalLogistic.mission.clients.resilient.ResilientUserService;
import org.orbitalLogistic.mission.dto.request.MissionRequestDTO;
import org.orbitalLogistic.mission.exceptions.MissionAlreadyExistsException;
import org.orbitalLogistic.mission.exceptions.MissionNotFoundException;
import org.orbitalLogistic.mission.exceptions.SpacecraftServiceNotFound;
import org.orbitalLogistic.mission.exceptions.UserServiceNotFound;
import org.orbitalLogistic.mission.mappers.MissionMapper;
import org.orbitalLogistic.mission.repositories.MissionAssignmentRepository;
import org.orbitalLogistic.mission.repositories.MissionRepository;
import org.orbitalLogistic.mission.entities.enums.MissionPriority;
import org.orbitalLogistic.mission.entities.enums.MissionType;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionServiceExceptionTest {

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private MissionAssignmentRepository missionAssignmentRepository;

    @Mock
    private MissionMapper missionMapper;

    @Mock
    private ResilientUserService userServiceClient;

    @Mock
    private ResilientSpacecraftService spacecraftServiceClient;

    @InjectMocks
    private MissionService missionService;

    private MissionRequestDTO request;

    @BeforeEach
    void setUp() {
        // DTO order: scheduledArrival, scheduledDeparture, spacecraftId, commandingOfficerId, priority, missionType, missionName, missionCode
        request = new MissionRequestDTO(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now(),
                2L,
                1L,
                MissionPriority.LOW,
                MissionType.CARGO_TRANSPORT,
                "Test",
                "CODE-1"
        );
    }

    @Test
    void getMissionById_NotFound() {
        when(missionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MissionNotFoundException.class, () -> missionService.getMissionById(1L));
    }

    @Test
    void createMission_AlreadyExists() {
        when(missionRepository.existsByMissionCode(request.missionCode())).thenReturn(true);
        assertThrows(MissionAlreadyExistsException.class, () -> missionService.createMission(request));
        verify(missionRepository).existsByMissionCode(request.missionCode());
    }

    @Test
    void createMission_UserNotFound() {
        when(missionRepository.existsByMissionCode(request.missionCode())).thenReturn(false);
        when(userServiceClient.userExists(request.commandingOfficerId())).thenReturn(false);

        assertThrows(UserServiceNotFound.class, () -> missionService.createMission(request));
    }

    @Test
    void createMission_SpacecraftNotFound() {
        when(missionRepository.existsByMissionCode(request.missionCode())).thenReturn(false);
        when(userServiceClient.userExists(request.commandingOfficerId())).thenReturn(true);
        when(spacecraftServiceClient.spacecraftExists(request.spacecraftId())).thenReturn(false);

        assertThrows(SpacecraftServiceNotFound.class, () -> missionService.createMission(request));
    }

    @Test
    void deleteMission_NotFound() {
        when(missionRepository.existsById(999L)).thenReturn(false);
        assertThrows(MissionNotFoundException.class, () -> missionService.deleteMission(999L));
    }
}
