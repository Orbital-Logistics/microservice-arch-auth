package org.orbitalLogistic.user.exceptions.auth;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
