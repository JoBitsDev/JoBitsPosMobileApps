package com.services.web_connections;

import com.utils.EnvironmentVariables;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckConnectionWCS {

    /**
     * URL con la coneccion al servidor.
     */
    private final String URLCONN = "http://" + EnvironmentVariables.getIP() + ":" + EnvironmentVariables.getPORT() + "/RM";

    public Boolean checkConnection() {
        URL url = null;
        try {
            url = new URL(URLCONN);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            // Starts the query
            con.setConnectTimeout(SimpleWebConnectionService.MAX_RESPONSE_TIME);//tiempo de espera maximo de la coneccion
            con.connect();
            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }//TODO: arreglar esto que no pincha bien

    }
}
