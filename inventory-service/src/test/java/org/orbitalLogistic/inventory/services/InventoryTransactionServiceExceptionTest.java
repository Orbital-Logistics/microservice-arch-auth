package org.orbitalLogistic.inventory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.inventory.clients.CargoServiceClient;
import org.orbitalLogistic.inventory.clients.SpacecraftServiceClient;
import org.orbitalLogistic.inventory.dto.request.InventoryTransactionRequestDTO;
import org.orbitalLogistic.inventory.entities.enums.TransactionType;
import org.orbitalLogistic.inventory.exceptions.InvalidOperationException;
import org.orbitalLogistic.inventory.repositories.InventoryTransactionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryTransactionServiceExceptionTest {

    @Mock
    private InventoryTransactionRepository inventoryTransactionRepository;

    @Mock
    private CargoServiceClient cargoServiceClient;

    @Mock
    private SpacecraftServiceClient spacecraftServiceClient;

    @InjectMocks
    private InventoryTransactionService inventoryTransactionService;

    private InventoryTransactionRequestDTO baseRequest;

    @BeforeEach
    void setUp() {
        baseRequest = mock(InventoryTransactionRequestDTO.class);
    }

    @Test
    void createTransaction_MissingCargoId() {
        when(baseRequest.cargoId()).thenReturn(null);
        assertThrows(InvalidOperationException.class, () -> inventoryTransactionService.createTransaction(baseRequest));
    }

    @Test
    void createTransaction_MissingUserId() {
        when(baseRequest.cargoId()).thenReturn(1L);
        when(baseRequest.performedByUserId()).thenReturn(null);
        assertThrows(InvalidOperationException.class, () -> inventoryTransactionService.createTransaction(baseRequest));
    }

    @Test
    void createTransaction_UnsupportedType() {
        when(baseRequest.cargoId()).thenReturn(1L);
        when(baseRequest.performedByUserId()).thenReturn(2L);
        when(baseRequest.transactionType()).thenReturn(null);
        assertThrows(InvalidOperationException.class, () -> inventoryTransactionService.createTransaction(baseRequest));
    }

    @Test
    void createTransaction_CargoValidationFails() {
        when(baseRequest.cargoId()).thenReturn(1L);
        when(baseRequest.performedByUserId()).thenReturn(2L);
        when(baseRequest.transactionType()).thenReturn(TransactionType.LOAD);

        when(cargoServiceClient.cargoExists(1L)).thenReturn(false);
        assertThrows(InvalidOperationException.class, () -> inventoryTransactionService.createTransaction(baseRequest));
    }
}
