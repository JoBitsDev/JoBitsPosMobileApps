package com.utils.loading;

/**
 * Capa: Utils
 * Clase que recibe por parametros el LoadingHandler, se sobreescribe el metodo process donde se
 * va a demorar la aplicacion.
 */
public interface LoadingProcess<T> {
    /**
     * Metodo a demorar de la aplicacion que mientras se ejecute tiene que estar el Dialog de
     * cargando activado.
     */
    T process() throws Exception;

    void post(T answer);
}