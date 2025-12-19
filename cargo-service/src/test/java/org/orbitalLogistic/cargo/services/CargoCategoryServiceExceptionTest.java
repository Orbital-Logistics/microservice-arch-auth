package org.orbitalLogistic.cargo.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.cargo.dto.request.CargoCategoryRequestDTO;
import org.orbitalLogistic.cargo.exceptions.CargoCategoryNotFoundException;
import org.orbitalLogistic.cargo.repositories.CargoCategoryRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargoCategoryServiceExceptionTest {

    @Mock
    private CargoCategoryRepository cargoCategoryRepository;

    @Mock
    private org.orbitalLogistic.cargo.mappers.CargoCategoryMapper cargoCategoryMapper;

    @InjectMocks
    private CargoCategoryService cargoCategoryService;

    @Test
    void createCategory_ParentNotFound() {
        var req = mock(CargoCategoryRequestDTO.class);
        when(req.parentCategoryId()).thenReturn(123L);
        when(cargoCategoryRepository.existsById(123L)).thenReturn(false);

        assertThrows(CargoCategoryNotFoundException.class, () -> cargoCategoryService.createCategory(req));
    }
}

