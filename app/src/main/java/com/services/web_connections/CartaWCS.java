package com.services.web_connections;

import com.utils.EnvironmentVariables;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Jorge on 2/8/18.
 */

public class CartaWCS extends SimpleWebConnectionService {


    private String p = "carta/";
    final String NOMBRE = "NOMBRE-REST";

    public CartaWCS() {
        super();
        path += p;
    }

    public String getNombreRest() throws Exception {
        String resp = connect(path + NOMBRE, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, String.class);
    }


}
