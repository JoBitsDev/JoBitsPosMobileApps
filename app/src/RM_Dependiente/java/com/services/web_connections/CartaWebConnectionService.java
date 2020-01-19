package com.services.web_connections;

import com.utils.EnvironmentVariables;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Jorge on 2/8/18.
 */

public class CartaWebConnectionService extends SimpleWebConnectionService {


    private String urlNombreRest = "http://" + EnvironmentVariables.getIP() + ":" + EnvironmentVariables.getPORT() + "/RM/rest/com.restmanager.carta/NOMBRE_REST";

    public CartaWebConnectionService() {
        super();
    }

    public String getNombreRest() {
        try {
            return connect(urlNombreRest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
