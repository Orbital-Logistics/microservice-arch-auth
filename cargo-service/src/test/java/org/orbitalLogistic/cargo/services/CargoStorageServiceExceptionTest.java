package org.orbitalLogistic.cargo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.cargo.dto.request.CargoStorageRequestDTO;
import org.orbitalLogistic.cargo.entities.Cargo;
import org.orbitalLogistic.cargo.entities.StorageUnit;
import org.orbitalLogistic.cargo.exceptions.CargoStorageNotFoundException;
import org.orbitalLogistic.cargo.exceptions.InsufficientCapacityException;
import org.orbitalLogistic.cargo.exceptions.StorageUnitNotFoundException;
import org.orbitalLogistic.cargo.exceptions.UserNotFoundException;
import org.orbitalLogistic.cargo.repositories.CargoRepository;
import org.orbitalLogistic.cargo.repositories.CargoStorageRepository;
import org.orbitalLogistic.cargo.repositories.StorageUnitRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargoStorageServiceExceptionTest {

    @Mock
    private CargoStorageRepository cargoStorageRepository;

    @Mock
    private org.orbitalLogistic.cargo.mappers.CargoStorageMapper cargoStorageMapper;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private StorageUnitRepository storageUnitRepository;

    @Mock
    private org.orbitalLogistic.cargo.clients.ResilientUserService userServiceClient;

    @InjectMocks
    private CargoStorageService cargoStorageService;

    private CargoStorageRequestDTO addRequest;

    @BeforeEach
    void setUp() {
        addRequest = mock(CargoStorageRequestDTO.class);
    }

    @Test
    void addCargoToStorage_StorageUnitNotFound() {
        when(storageUnitRepository.findById(1L)).thenReturn(Optional.empty());
        when(addRequest.storageUnitId()).thenReturn(1L);
        assertThrows(StorageUnitNotFoundException.class, () -> cargoStorageService.addCargoToStorage(addRequest));
    }

    @Test
    void addCargoToStorage_CargoNotFound() {
        StorageUnit unit = new StorageUnit();
        when(storageUnitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(cargoRepository.findById(2L)).thenReturn(Optional.empty());
        when(addRequest.storageUnitId()).thenReturn(1L);
        when(addRequest.cargoId()).thenReturn(2L);

        assertThrows(CargoStorageNotFoundException.class, () -> cargoStorageService.addCargoToStorage(addRequest));
    }

    @Test
    void addCargoToStorage_UserNotFound() {
        StorageUnit unit = new StorageUnit();
        Cargo cargo = new Cargo();
        cargo.setMassPerUnit(BigDecimal.ONE);
        cargo.setVolumePerUnit(BigDecimal.ONE);

        when(storageUnitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(cargoRepository.findById(2L)).thenReturn(Optional.of(cargo));
        when(addRequest.storageUnitId()).thenReturn(1L);
        when(addRequest.cargoId()).thenReturn(2L);
        when(addRequest.updatedByUserId()).thenReturn(5L);

        when(userServiceClient.userExists(5L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> cargoStorageService.addCargoToStorage(addRequest));
    }

    @Test
    void addCargoToStorage_InsufficientCapacity() {
        StorageUnit unit = new StorageUnit();
        unit.setTotalMassCapacity(BigDecimal.ONE);
        unit.setTotalVolumeCapacity(BigDecimal.ONE);
        unit.setCurrentMass(BigDecimal.ZERO);
        unit.setCurrentVolume(BigDecimal.ZERO);

        Cargo cargo = new Cargo();
        cargo.setMassPerUnit(BigDecimal.TEN);
        cargo.setVolumePerUnit(BigDecimal.TEN);

        when(storageUnitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(cargoRepository.findById(2L)).thenReturn(Optional.of(cargo));
        when(addRequest.storageUnitId()).thenReturn(1L);
        when(addRequest.cargoId()).thenReturn(2L);
        when(addRequest.updatedByUserId()).thenReturn(5L);
        when(addRequest.quantity()).thenReturn(1);

        when(userServiceClient.userExists(5L)).thenReturn(true);

        assertThrows(InsufficientCapacityException.class, () -> cargoStorageService.addCargoToStorage(addRequest));
    }

    @Test
    void updateQuantity_NotFound() {
        when(cargoStorageRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(CargoStorageNotFoundException.class, () -> cargoStorageService.updateQuantity(999L, addRequest));
    }
}

