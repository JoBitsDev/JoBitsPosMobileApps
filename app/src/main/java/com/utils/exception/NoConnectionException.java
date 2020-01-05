package com.utils.exception;

/**
 * Clase: Services
 * Esta el la excepcion que se lanza cuando no existe coneccion con el servidor.
 * En realidad la que se lanza es {@link InterruptedException } pero se castea a esta para tener
 * un mejor control sobre la excepcion en caso que se necesite.
 *
 * @extends InterruptedException.
 */
public class NoConnectionException extends InterruptedException {
}
