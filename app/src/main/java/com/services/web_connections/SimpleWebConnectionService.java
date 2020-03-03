package com.services.web_connections;

import java.io.*;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.exception.*;

import java.net.HttpURLConnection;

import com.utils.EnvironmentVariables;

/**
 * Capa: Services
 * Clase base para TODOS los servicios de la aplicación.
 * TODAS las activitys extienden de esta clase y proporciona metodos basicos para todas
 * como conectarse o los fetch.
 */

public class SimpleWebConnectionService {

    private final String AUTHORITATION = "Authorization";
    private final String BEARER = "Bearer ";

    /**
     * Token para las llamandas seguras al servidor.
     */
    protected static String TOKEN = null;

    /**
     * Tiempo maximo esperado para la lectura.
     */
    public static final int MAX_READ_TIME = 5 * 1000;

    protected final ObjectMapper om = new ObjectMapper();

    /**
     * Tiempo maximo esperado para la respuesta del servidor.
     */
    public static final int MAX_RESPONSE_TIME = 3 * 1000;

    /**
     * Partes de la URL de las consultas.
     */
    protected String
            ip,
            port,
            path;

    /**
     * Coneccion.
     */
    protected HttpURLConnection con = null;

    /**
     * URL.
     */
    protected URL url = null;

    /**
     * Constructor por defecto. Carga el IP y el Puerto de la configuracion por defecto de las
     * variables de entorno.
     */
    public SimpleWebConnectionService() {
        this.ip = EnvironmentVariables.getIP();
        port = EnvironmentVariables.getPORT();
        path = "http://" + ip + ":" + port + "/" + EnvironmentVariables.STARTPATH;
    }

    /**
     * Metodo a usar para la coneccion al servicio.
     * Manda por POST la peticion a la url con el body especifico y el token de segurdad en el header
     *
     * @param urlToExcecute URL a ejecutar la peticion
     * @param body          Cuerpo del mensaje, JSON con la info.
     * @param token         Token de seguridad.
     * @return String con el formato JSON.
     * @throws Exception Si algo sale mal.
     */
    public String connect(final String urlToExcecute, final String body, final String token, HTTPMethod method) throws Exception {
        //Set up the connection
        String resp = "";
        URL url = new URL(urlToExcecute);
        con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(method != HTTPMethod.GET);
        con.setRequestMethod(method.getMethod());
        con.setRequestProperty("Content-Type", "text/plain");
        con.setReadTimeout(MAX_READ_TIME);
        con.setConnectTimeout(MAX_RESPONSE_TIME);
        con.setRequestProperty(AUTHORITATION, BEARER + token);

        // Starts the query
        if (method != HTTPMethod.GET) {
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(body == null ? "" : body);
            os.flush();
            os.close();
        }
        int code = con.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {//si esta ok lee el JSON
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(con.getInputStream()),
                    8192);
            String linea;
            while ((linea = input.readLine()) != null) {
                resp += linea;
            }
            con.disconnect();
            input.close();
            //os.close();
            return resp;
        } else {//Si no, lee el error y lo propaga
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()),
                    8192);
            String linea;
            while ((linea = input.readLine()) != null) {
                resp += linea;
            }
            input.close();
            con.disconnect();
            //os.close();
            throw new ServerErrorException(resp, code);
        }
    }

}

