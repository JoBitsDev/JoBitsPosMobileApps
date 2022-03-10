package com.utils.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * JoBits
 *
 * @author Jorge
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {

    private int status;
    private String message;
    private List<String> errors;

    public ApiError() {
    }

    public ApiError(int status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(int status, String message, String... error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.status);
        hash = 47 * hash + Objects.hashCode(this.message);
        hash = 47 * hash + Objects.hashCode(this.errors);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApiError other = (ApiError) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.errors, other.errors)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ApiError{" + "status=" + status + ", message=" + message + '}';
    }

}
