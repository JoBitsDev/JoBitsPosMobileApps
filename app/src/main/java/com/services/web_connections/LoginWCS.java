package com.services.web_connections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.CredentialsModel;
import com.services.models.RequestModel;
import com.services.models.RequestType;
import com.utils.EnvironmentVariables;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

/**
 * Capa: Services.
 * Esta clase es la encargada del login de un usuario en el sistema, en su creacion recive el
 * usuario y la contrasenna y proporciona un metodo para autenticarlos.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */

public class LoginWCS extends SimpleWebConnectionService {

    private final String login_URL;

    private CredentialsModel credentials;

    /**
     * Constructor del servicio, recive el usuario y la contrasenna de los que se van a logear.
     */
    public LoginWCS() {
        super();
        this.login_URL = path + "login/AUTH";
    }

    /**
     * Autentica al usuario.
     *
     * @return true si el usuario se auntentica correctamente, false de lo contrario.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean authenticate(String user, String pass) throws Exception {
        credentials = new CredentialsModel(user, pass);
        String body = new ObjectMapper().writeValueAsString(credentials);
        RequestModel req = new RequestModel(login_URL, body, null, HTTPMethod.POST, RequestType.LOGIN);
        if (EnvironmentVariables.ONLINE) {
            TOKEN = connect(req);
        } else {
            TOKEN = "TOKEN-" + System.currentTimeMillis();
            addRequestToQueque(req);
        }
        return true;
    }

    public String getToken() {
        return TOKEN;
    }

    public void setToken(String token) {
        TOKEN = token;
    }
}
