package com.utils.exception;

import java.util.concurrent.ExecutionException;

/**
 * Clase: Services
 * Esta el la excepcion que se lanza cuando ocurre un error en el servidor.
 * En realidad la que se lanza es {@link ExecutionException } pero se castea a esta para tener
 * un mejor control sobre la excepcion en caso que se necesite. Ademas que ExecutionException
 * no permite instanciacion directa.
 *
 * @extends ExecutionException.
 */
public class ServerErrorException extends ExecutionException {
    private final int code;

    private ApiError apiError;

    public ServerErrorException(ApiError error) {
        super(error.getMessage());
        this.apiError = error;
        this.code = apiError.getStatus();
    }

    public ServerErrorException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServerErrorException() {
        code = 0;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public int getCode() {
        return code;
    }
}
