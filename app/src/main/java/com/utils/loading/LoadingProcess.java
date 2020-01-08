package com.utils.loading;

/**
 * Capa: Utils
 * Clase que recibe por parametros el LoadingHandler, se sobreescribe el metodo process donde se
 * va a demorar la aplicacion.
 */
public abstract class LoadingProcess<T> {
    /**
     * Metodo a demorar de la aplicacion que mientras se ejecute tiene que estar el Dialog de
     * cargando activado.
     */
    public abstract T process() throws Exception;
}