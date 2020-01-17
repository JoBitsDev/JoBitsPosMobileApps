package com.controllers;

import com.services.web_connections.CheckConnectionWCS;

/**
 * Capa: Controllers
 * Clase base abstracta para TODAS los controllers de la aplicación.
 * TODOS los controllers extienden de esta clase y proporciona metodos basicos para todas como
 * chequear la coneccion con el servidor.
 */
public abstract class BaseController {

    /**
     * Chequea la coneccion con el servidor, hace un ping y verifica respuesta.
     * En caso de CUALQUIER error con el servido lo maneja y da false.
     *
     * @return true si hay coneccion con el servidor, false de lo contrario.
     */
    public boolean checkConnection() {
        try {
            return new CheckConnectionWCS().checkConnection();
        } catch (Exception e) {
            return false;
        }
    }
}
