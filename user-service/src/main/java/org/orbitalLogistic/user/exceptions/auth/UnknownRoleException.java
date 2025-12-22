package org.orbitalLogistic.user.exceptions.auth;

public class UnknownRoleException extends RuntimeException {
    public UnknownRoleException(String message) {
        super(message);
    }
}
