package org.geoandri.teams.exception;

public class TeamPersistenceException extends Exception {
    public TeamPersistenceException() {
    }

    public TeamPersistenceException(String message) {
        super(message);
    }

    public TeamPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
