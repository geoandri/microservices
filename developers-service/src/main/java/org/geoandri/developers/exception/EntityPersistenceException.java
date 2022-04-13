package org.geoandri.developers.exception;

public class EntityPersistenceException extends Exception {
    public EntityPersistenceException() {
    }

    public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
