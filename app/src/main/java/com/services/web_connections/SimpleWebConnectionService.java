package com.services.web_connections;

import android.os.AsyncTask;

import java.io.*;
import java.net.URL;

import com.utils.exception.*;

import java.net.HttpURLConnection;

import com.utils.EnvironmentVariables;

import java.util.concurrent.ExecutionException;

/**
 * Capa: Services
 * Clase base para TODOS los servicios de la aplicación.
 * TODAS las activitys extienden de esta clase y proporciona metodos basicos para todas
 * como conectarse o los fetch.
 */

public class SimpleWebConnectionService {

    /**
     * Partes de la URL de las consultas.
     */
    protected String
            ip,
            port,
            path,
            resp = null;

    /**
     * Coneccion.
     */
    protected HttpURLConnection con = null;

    /**
     * URL.
     */
    protected URL url = null;

    /**
     * Constructor que solicita ip y puerto para en caso de que se quiera conectar a otro lugar.
     * @param ip del servidor donde se hacen las peticiones.
     * @param port del servidor donde se hacen las peticiones.
     */
    public SimpleWebConnectionService(String ip, String port) {
        this.ip = ip;
        this.port = port;
        path = "http://" + ip + ":" + port + "/" + EnvironmentVariables.STARTPATH;
    }

    /**
     * Constructor por defecto. Carga el IP y el Puerto de la configuracion por defecto de las
     * variables de entorno.
     */
    public SimpleWebConnectionService() {
        this.ip = EnvironmentVariables.IP;
        port = EnvironmentVariables.PORT;
        path = "http://" + ip + ":" + port + "/" + EnvironmentVariables.STARTPATH;
    }

    /**
     * Devuelve la informacion de la consulta a la URL pasada por parametro.
     *
     * @param url a consultar
     * @return Respuesta de la consulta
     * @throws ServerErrorException si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String connect(String url) throws ServerErrorException, NoConnectionException {
        fetchData f = new fetchData();
        f.execute(url);
        String res = null;

        try {
            res = f.get();
        } catch (InterruptedException e) {//convierte las excepciones que manda a las que manejamos
            throw new NoConnectionException();
        } catch (ExecutionException e) {
            throw new ServerErrorException();
        }

        if (res == null) {
            throw new ServerErrorException();
        } else if (res.matches(EnvironmentVariables.PETITION_ERROR)) {
            throw new NoConnectionException();
        } else {
            return res;
        }
    }

    /**
     * Capa: Interna
     * Clase que sealiza Asyncronicamente la peticion al servidor.
     * @extends AsyncTask<String, Void, String> ya que es una tarea asincrona.
     */
    protected class fetchData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            try {
                String ret = downloadUrl(url[0]);
                return ret;
            } catch (IOException e) {
                e.printStackTrace();
                return EnvironmentVariables.PETITION_ERROR;
            }

        }

        private String downloadUrl(String urlString) throws IOException {
            url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            // Starts the query
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(con.getInputStream()),
                        8192);
                resp = input.readLine();
                input.close();
                con.disconnect();
                return resp;
            } else {
                return null;
            }
        }
    }

}

