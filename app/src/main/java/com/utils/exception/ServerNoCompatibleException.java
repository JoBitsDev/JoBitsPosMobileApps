package com.utils.exception;

import java.util.concurrent.ExecutionException;

/**
 * Clase: Services
 * Esta el la excepcion que se lanza cuando la version del servidor no es compatible con la que requiere la app.
 * En realidad la que se lanza es {@link ExecutionException } pero se castea a esta para tener
 * un mejor control sobre la excepcion en caso que se necesite. Ademas que ExecutionException
 * no permite instanciacion directa.
 *
 * @extends ExecutionException.
 */
public class ServerNoCompatibleException extends ExecutionException {
    public ServerNoCompatibleException(String message) {
        super(message);
    }

    public ServerNoCompatibleException() {
    }
}
