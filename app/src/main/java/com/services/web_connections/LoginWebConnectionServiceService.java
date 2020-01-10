package com.services.web_connections;

import android.content.res.Resources;

import com.activities.R;
import com.utils.EnvironmentVariables;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.concurrent.ExecutionException;

/**
 * Capa: Services.
 * Esta clase es la encargada del login de un usuario en el sistema, en su creacion recive el
 * usuario y la contrasenna y proporciona un metodo para autenticarlos.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */

public class LoginWebConnectionServiceService extends SimpleWebConnectionService {

    /**
     * Nombre de usuario a autenticar.
     */
    private String user;

    /**
     * Contrasenna del usuario a autenticar.
     */
    private String pass;

    /**
     * Nivel de acceso de la aplicacion
     */
    private String access;

    /**
     * Constructor del servicio, recive el usuario y la contrasenna de los que se van a logear.
     *
     * @param user usuario a autenticar.
     * @param pass contrasenna del usuario.
     * @param access nivel de acceso de la aplicacion
     */
    public LoginWebConnectionServiceService(String user, String pass, String access) {
        super();
        this.user = user;
        this.pass = pass;
        this.access = access;
    }

    /**
     * Autentica al usuario.
     *
     * @return true si el usuario se auntentica correctamente, false de lo contrario.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean authenticate() throws ServerErrorException, NoConnectionException {
        return connect(path + "com.restmanager.personal/l_" + user + "_" + pass + "_" + access).equals(EnvironmentVariables.PETITION_TRUE);
    }

}
