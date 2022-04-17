package org.geoandri.developers.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
    }

    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
