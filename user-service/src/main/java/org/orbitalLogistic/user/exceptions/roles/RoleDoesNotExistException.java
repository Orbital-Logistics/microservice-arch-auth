package org.orbitalLogistic.user.exceptions.roles;

public class RoleDoesNotExistException extends RuntimeException {
    public RoleDoesNotExistException(String message) {
        super(message);
    }
}
