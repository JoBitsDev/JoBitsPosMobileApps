package com.controllers;

import com.services.web_connections.SimpleWebConnectionService;
import com.utils.exception.*;
import com.services.web_connections.LoginWebConnectionServiceService;

import java.util.concurrent.TimeoutException;

/**
 * Capa: Controllers
 * Clase controladora de LoginActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller plicacion.
 */
public class LoginController extends BaseController {

    /**
     * Accion de logueo, trata de loguear este nombre de usuario con este contrasenna en el sistema.
     *
     * @param username Nombre de usuario.
     * @param password Contrasenna.
     * @return true si se loguea exitosamente, false de lo contrario.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean loginAction(String username, String password) throws Exception {
        LoginWebConnectionServiceService login = new LoginWebConnectionServiceService(username, password);
        login.authenticate();
        return true;
    }
}
