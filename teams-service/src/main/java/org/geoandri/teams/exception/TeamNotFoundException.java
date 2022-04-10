package org.geoandri.teams.exception;

import java.io.Serializable;

public class TeamNotFoundException extends Exception implements Serializable {

    public TeamNotFoundException() {
    }

    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
