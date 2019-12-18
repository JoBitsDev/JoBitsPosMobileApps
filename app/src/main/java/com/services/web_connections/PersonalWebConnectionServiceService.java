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
        super(EnvironmentVariables.IP, "8080");
        path += P;
    }

    public String [] getUsiariosActivos(){
    String [] result = {};
        try {
            String ret = connect(path + usuariosActivos);

            if(ret != null)
            result = ret.substring(0,ret.length()-1).split(",");

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
