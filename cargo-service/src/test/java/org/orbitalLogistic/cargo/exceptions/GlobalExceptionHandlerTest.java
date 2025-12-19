package org.orbitalLogistic.cargo.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleCargoNotFound() {
        CargoNotFoundException ex = new CargoNotFoundException("Cargo not found X");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleCargoNotFoundException(ex);
        assertEquals(404, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Cargo not found X", resp.getBody().message());
        assertEquals("Not Found", resp.getBody().error());
    }

    @Test
    void handleCargoAlreadyExists() {
        CargoAlreadyExistsException ex = new CargoAlreadyExistsException("Already exists");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleCargoAlreadyExistsException(ex);
        assertEquals(409, resp.getStatusCodeValue());
        assertEquals("Already exists", resp.getBody().message());
        assertEquals("Conflict", resp.getBody().error());
    }

    @Test
    void handleCargoCategoryNotFound() {
        CargoCategoryNotFoundException ex = new CargoCategoryNotFoundException("Category missing");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleCargoCategoryNotFoundException(ex);
        assertEquals(404, resp.getStatusCodeValue());
        assertEquals("Category missing", resp.getBody().message());
    }

    @Test
    void handleStorageUnitNotFound() {
        StorageUnitNotFoundException ex = new StorageUnitNotFoundException("SU missing");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleStorageUnitNotFoundException(ex);
        assertEquals(404, resp.getStatusCodeValue());
        assertEquals("SU missing", resp.getBody().message());
    }

    @Test
    void handleUserNotFound() {
        UserNotFoundException ex = new UserNotFoundException("User not found 1");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleUserNotFoundException(ex);
        assertEquals(404, resp.getStatusCodeValue());
        assertEquals("User not found 1", resp.getBody().message());
    }

    @Test
    void handleUserServiceException() {
        UserServiceException ex = new UserServiceException("Service down");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleUserServiceException(ex);
        assertEquals(503, resp.getStatusCodeValue());
        assertEquals("Service down", resp.getBody().message());
        assertEquals("Server unavailable", resp.getBody().error());
    }

    @Test
    void handleStorageUnitAlreadyExists() {
        StorageUnitAlreadyExistsException ex = new StorageUnitAlreadyExistsException("SU exists");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleStorageUnitAlreadyExistsException(ex);
        assertEquals(409, resp.getStatusCodeValue());
        assertEquals("SU exists", resp.getBody().message());
    }

    @Test
    void handleCargoStorageNotFound() {
        CargoStorageNotFoundException ex = new CargoStorageNotFoundException("CS missing");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleCargoStorageNotFoundException(ex);
        assertEquals(404, resp.getStatusCodeValue());
        assertEquals("CS missing", resp.getBody().message());
    }

    @Test
    void handleInsufficientCapacity() {
        InsufficientCapacityException ex = new InsufficientCapacityException("no capacity");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleInsufficientCapacityException(ex);
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("no capacity", resp.getBody().message());
        assertEquals("Bad Request", resp.getBody().error());
    }

    @Test
    void handleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("bad arg");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> resp = handler.handleIllegalArgument(ex);
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("bad arg", resp.getBody().message());
    }
}

