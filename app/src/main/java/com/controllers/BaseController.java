package com.controllers;

import java.net.*;
import java.io.IOException;

import android.os.AsyncTask;

import com.utils.EnvironmentVariables;

/**
 * Capa: Controllers
 * Clase base abstracta para TODAS los controllers de la aplicación.
 * TODOS los controllers extienden de esta clase y proporciona metodos basicos para todas como
 * chequear la coneccion con el servidor.
 */
public abstract class BaseController {

    /**
     * URL con la coneccion al servidor.
     */
    protected final String URLCONN = "http://" + EnvironmentVariables.IP + ":" + EnvironmentVariables.PORT + "/RM";

    /**
     * Chequea la coneccion con el servidor, hace un ping y verifica respuesta.
     * En caso de CUALQUIER error con el servido lo maneja y da false.
     *
     * @return true si hay coneccion con el servidor, false de lo contrario.
     */
    public boolean checkConnection() {
        try {
            return checkConnection(URLCONN);
        } catch (Exception e) {
            return false;
        }
    }

    protected Boolean checkConnection(String uri) {
        URL url = null;
        try {
            url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            // Starts the query
            con.setConnectTimeout(500);//tiempo de espera maximo de la coneccion
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
