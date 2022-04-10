package org.geoandri.developers.exception.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ErrorMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public ErrorMessage() {
    }

    public ErrorMessage(List<String> errors) {
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
