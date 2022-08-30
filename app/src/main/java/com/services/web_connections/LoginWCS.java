package com.services.web_connections;

import com.services.models.CredentialsModel;
import com.services.models.Token;
import com.services.web_connections.interfaces.LoginWCI;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.Map;

import retrofit2.Response;

/**
 * Capa: Services.
 * Esta clase es la encargada del login de un usuario en el sistema, en su creacion recive el
 * usuario y la contrasenna y proporciona un metodo para autenticarlos.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */

public class LoginWCS extends RetrofitBaseConection {

    LoginWCI loginService = retrofit.create(LoginWCI.class);


    private CredentialsModel credentials;

    /**
     * Constructor del servicio, recive el usuario y la contrasenna de los que se van a logear.
     */
    public LoginWCS() {
        super();
    }

    /**
     * Autentica al usuario.
     *
     * @return true si el usuario se auntentica correctamente, false de lo contrario.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean authenticate(String user, String pass) throws Exception {
        Response<Map<String,Object>> ret = loginService.getToken(TENNANT_TOKEN,
                HTTP_HEADER_BASIC + new CredentialsModel(user, pass).getBase64BasicAuth()).execute();
        if (ret.isSuccessful()) {
            TOKEN = ret.body().get("Token").toString();
            return true;
        } else {
            throw new ServerErrorException(ret.errorBody().string(), ret.code());
        }
    }

    public String getToken() {
        return TOKEN;
    }

    public void setToken(String token) {
        TOKEN = token;
    }

    /**
     * Obtiene el token de la base de datos
     *
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean getTennantToken(String user, String pass, int idUser, int idBaseDatos) throws Exception {
        Response<Token> res = loginService.getTennantToken(HTTP_HEADER_BASIC + new CredentialsModel(user, pass).getBase64BasicAuth(), idUser, idBaseDatos).execute();
        if (res.isSuccessful()) {
            TENNANT_TOKEN = res.body().getToken();
            return true;
        }

        throw new ServerErrorException(res.errorBody().string(), res.code());

    }
}
