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
    private final String URL_GET_TENNANT_TOKEN;

    private CredentialsModel credentials;

    /**
     * Constructor del servicio, recive el usuario y la contrasenna de los que se van a logear.
     */
    public LoginWCS() {
        super();
        this.login_URL = path + "login/AUTH";
        this.URL_GET_TENNANT_TOKEN = path + "login/GET-TENNANT-TOKEN";
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
        RequestModel req = new RequestModel(login_URL, credentials.toString(), TENNANT_TOKEN,TOKEN, HTTPMethod.POST, RequestType.LOGIN);
        if (EnvironmentVariables.ONLINE) {
            TOKEN = connect(req);
        } else {
            TOKEN = "TOKEN";
            req.setUid(TOKEN);
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

    public void getTennantToken(String user, String pass) throws Exception {
        CredentialsModel credentials = new CredentialsModel(user,pass);
        RequestModel req = new RequestModel(URL_GET_TENNANT_TOKEN, credentials.toString(), TENNANT_TOKEN,TOKEN, HTTPMethod.POST, RequestType.TENNANT);
        TENNANT_TOKEN = connect(req);
    }
}
