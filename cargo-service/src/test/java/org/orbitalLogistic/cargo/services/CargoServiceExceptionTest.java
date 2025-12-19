package org.orbitalLogistic.cargo.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.cargo.dto.request.CargoRequestDTO;
import org.orbitalLogistic.cargo.exceptions.CargoAlreadyExistsException;
import org.orbitalLogistic.cargo.exceptions.CargoNotFoundException;
import org.orbitalLogistic.cargo.repositories.CargoRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargoServiceExceptionTest {

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private org.orbitalLogistic.cargo.mappers.CargoMapper cargoMapper;

    @Mock
    private CargoCategoryService cargoCategoryService;

    @Mock
    private CargoStorageService cargoStorageService;

    @InjectMocks
    private CargoService cargoService;

    @Test
    void createCargo_AlreadyExists() {
        var req = mock(CargoRequestDTO.class);
        when(req.name()).thenReturn("Cargo-1");
        when(cargoRepository.existsByName("Cargo-1")).thenReturn(true);
        assertThrows(CargoAlreadyExistsException.class, () -> cargoService.createCargo(req));
    }

    @Test
    void getCargoById_NotFound() {
        when(cargoRepository.findById(5L)).thenReturn(java.util.Optional.empty());
        assertThrows(CargoNotFoundException.class, () -> cargoService.getCargoById(5L));
    }

    @Test
    void updateCargo_NotFound() {
        var req = mock(CargoRequestDTO.class);
        when(cargoRepository.findById(99L)).thenReturn(java.util.Optional.empty());
        assertThrows(CargoNotFoundException.class, () -> cargoService.updateCargo(99L, req));
    }

    @Test
    void deleteCargo_NotFound() {
        when(cargoRepository.existsById(123L)).thenReturn(false);
        assertThrows(CargoNotFoundException.class, () -> cargoService.deleteCargo(123L));
    }
}

