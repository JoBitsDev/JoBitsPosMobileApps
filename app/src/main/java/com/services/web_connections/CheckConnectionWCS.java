package com.services.web_connections;

import com.utils.EnvironmentVariables;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckConnectionWCS extends SimpleWebConnectionService {

    /**
     * URL con la coneccion al servidor.
     */
    private final String URLCONN = "http://" + EnvironmentVariables.getIP() + ":" + EnvironmentVariables.getPORT() + "/jobits";

    public Boolean checkConnection() {
        try {
            String resp = connect(URLCONN, null, null, HTTPMethod.GET);
            return om.readValue(resp, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }
}
