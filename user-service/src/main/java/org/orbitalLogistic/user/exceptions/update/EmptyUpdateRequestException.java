package org.orbitalLogistic.user.exceptions.update;

public class EmptyUpdateRequestException extends RuntimeException {
    public EmptyUpdateRequestException(String message) {
        super(message);
    }
}
