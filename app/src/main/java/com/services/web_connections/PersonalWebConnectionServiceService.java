package com.services.web_connections;

import com.utils.EnvironmentVariables;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 15/7/18.
 */

public class PersonalWebConnectionServiceService extends SimpleWebConnectionService {


    String P = "com.restmanager.personal/";
    String usuariosActivos = "MOSTRAR_PERSONAL_TRABAJANDO";

    public PersonalWebConnectionServiceService() {
        super();
        path += P;
    }

    public String[] getUsiariosActivos() throws InterruptedException, ExecutionException {
        String[] result = {};
        String ret = connect(path + usuariosActivos);

        if (ret != null)
            result = ret.substring(0, ret.length() - 1).split(",");

        return result;
    }
}
