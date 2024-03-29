package com.controllers;

import com.services.web_connections.GeneralWCS;
import com.services.web_connections.LoginWCS;
import com.utils.EnvironmentVariables;

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
    public boolean checkConnection() throws Exception {
       return new LoginWCS().getTennantToken(
               EnvironmentVariables.getUsuarioTennant(),
                EnvironmentVariables.getPassTennant(),
                EnvironmentVariables.getUsuarioId(),
                EnvironmentVariables.getBaseDatosId());
    }

    public Boolean uploadQueque() throws Exception {
        return new GeneralWCS().uploadQueque();
    }
}
