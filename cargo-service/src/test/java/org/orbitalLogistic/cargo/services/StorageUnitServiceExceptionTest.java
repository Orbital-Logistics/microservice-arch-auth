package org.orbitalLogistic.cargo.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.cargo.dto.request.StorageUnitRequestDTO;
import org.orbitalLogistic.cargo.exceptions.StorageUnitAlreadyExistsException;
import org.orbitalLogistic.cargo.exceptions.StorageUnitNotFoundException;
import org.orbitalLogistic.cargo.repositories.StorageUnitRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageUnitServiceExceptionTest {

    @Mock
    private StorageUnitRepository storageUnitRepository;

    @Mock
    private org.orbitalLogistic.cargo.mappers.StorageUnitMapper storageUnitMapper;

    @Mock
    private org.orbitalLogistic.cargo.repositories.CargoStorageRepository cargoStorageRepository;

    @Mock
    private CargoStorageService cargoStorageService;

    @InjectMocks
    private StorageUnitService storageUnitService;

    @Test
    void getStorageUnitById_NotFound() {
        when(storageUnitRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(StorageUnitNotFoundException.class, () -> storageUnitService.getStorageUnitById(1L));
    }

    @Test
    void createStorageUnit_AlreadyExists() {
        var req = mock(StorageUnitRequestDTO.class);
        when(req.unitCode()).thenReturn("UC-1");
        when(storageUnitRepository.existsByUnitCode("UC-1")).thenReturn(true);
        assertThrows(StorageUnitAlreadyExistsException.class, () -> storageUnitService.createStorageUnit(req));
    }

    @Test
    void updateStorageUnit_NotFound() {
        var req = mock(StorageUnitRequestDTO.class);
        when(storageUnitRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        assertThrows(StorageUnitNotFoundException.class, () -> storageUnitService.updateStorageUnit(999L, req));
    }

    @Test
    void getStorageUnitInventory_NotFound() {
        when(storageUnitRepository.existsById(999L)).thenReturn(false);
        assertThrows(StorageUnitNotFoundException.class, () -> storageUnitService.getStorageUnitInventory(999L, 0, 10));
    }
}

