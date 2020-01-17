package com.services.web_connections;

import java.io.*;
import java.net.URL;

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
     *
     * @param ip   del servidor donde se hacen las peticiones.
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
     * @Deprecated Usar en ves de esto usar {@connectPost} y trabajar con JSONs
     * @param url a consultar
     * @return Respuesta de la consulta
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    @Deprecated
    public String connect(final String url) throws Exception {
        String res = execute(url);

        if (res == null) {
            throw new ServerErrorException();
        } else if (res.matches(EnvironmentVariables.PETITION_ERROR)) {
            throw new NoConnectionException();
        } else {
            return res;
        }
    }

    /**
     * Ejecuta la consulta en el URL por GET.
     *
     * @Deprecated Usar en ves de esto usar {@connectPost} y trabajar con JSONs
     * @param urlToExcecute
     * @return el String con la respuesta.
     */
    @Deprecated
    protected String execute(String urlToExcecute) {
        try {
            URL url = new URL(urlToExcecute);
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            // Starts the query
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(con.getInputStream()),
                        8192);
                resp = "";
                String linea;
                while ((linea = input.readLine()) != null) {
                    resp += linea;
                }
                con.disconnect();
                input.close();
                return resp;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return EnvironmentVariables.PETITION_ERROR;
        }
    }

    /**
     * Metodo a usar para la coneccion al servicio.
     * Manda por POST la peticion a la url con el body especifico y el token de segurdad en el header
     *
     * @param urlToExcecute URL a ejecutar la peticion
     * @param body Cuerpo del mensaje, JSON con la info.
     * @param token Token de seguridad.
     * @return String con el formato JSON.
     * @throws Exception Si algo sale mal.
     */
    public String connectPost(final String urlToExcecute, final String body, final String token) throws Exception {
        //Set up the connection
        URL url = new URL(urlToExcecute);
        con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setReadTimeout(MAX_READ_TIME);
        con.setConnectTimeout(MAX_RESPONSE_TIME);
        con.setRequestProperty(AUTHORITATION, BEARER + token);
        
        // Starts the query
        OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
        os.write(body);
        os.flush();

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {//si esta ok lee el JSON
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(con.getInputStream()),
                    8192);
            resp = "";
            String linea;
            while ((linea = input.readLine()) != null) {
                resp += linea;
            }
            con.disconnect();
            input.close();
            os.close();
            return resp;
        } else {//Si no, lee el error y lo propaga
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()),
                    8192);
            resp = input.readLine();
            input.close();
            con.disconnect();
            os.close();
            throw new ServerErrorException(resp);
        }
    }

}

