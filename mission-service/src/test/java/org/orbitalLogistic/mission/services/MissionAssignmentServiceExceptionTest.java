package org.orbitalLogistic.mission.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.mission.clients.resilient.ResilientUserService;
import org.orbitalLogistic.mission.dto.request.MissionAssignmentRequestDTO;
import org.orbitalLogistic.mission.entities.Mission;
import org.orbitalLogistic.mission.entities.MissionAssignment;
import org.orbitalLogistic.mission.exceptions.InvalidOperationException;
import org.orbitalLogistic.mission.exceptions.MissionAssignmentNotFoundException;
import org.orbitalLogistic.mission.exceptions.MissionNotFoundException;
import org.orbitalLogistic.mission.mappers.MissionAssignmentMapper;
import org.orbitalLogistic.mission.repositories.MissionAssignmentRepository;
import org.orbitalLogistic.mission.repositories.MissionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionAssignmentServiceExceptionTest {

    @Mock
    private MissionAssignmentRepository missionAssignmentRepository;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private MissionAssignmentMapper missionAssignmentMapper;

    @Mock
    private ResilientUserService userServiceClient;

    @InjectMocks
    private MissionAssignmentService missionAssignmentService;

    private MissionAssignmentRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new MissionAssignmentRequestDTO(
                1L,
                2L,
                null,
                "Zone"
        );
    }

    @Test
    void createAssignment_MissionNotFound() {
        when(missionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MissionNotFoundException.class, () -> missionAssignmentService.createAssignment(request));
    }

    @Test
    void createAssignment_UserNotFound() {
        when(missionRepository.findById(1L)).thenReturn(Optional.of(new Mission()));
        when(userServiceClient.userExists(2L)).thenReturn(false);

        assertThrows(InvalidOperationException.class, () -> missionAssignmentService.createAssignment(request));
    }

    @Test
    void createAssignment_AlreadyAssigned() {
        when(missionRepository.findById(1L)).thenReturn(Optional.of(new Mission()));
        when(userServiceClient.userExists(2L)).thenReturn(true);
        when(missionAssignmentRepository.findByMissionIdAndUserId(1L, 2L)).thenReturn(List.of(new MissionAssignment()));

        assertThrows(InvalidOperationException.class, () -> missionAssignmentService.createAssignment(request));
    }

    @Test
    void deleteAssignment_NotFound() {
        when(missionAssignmentRepository.existsById(999L)).thenReturn(false);
        assertThrows(MissionAssignmentNotFoundException.class, () -> missionAssignmentService.deleteAssignment(999L));
    }
}
