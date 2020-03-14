package com.services.web_connections;

import java.io.*;
import java.net.URL;

import com.activities.BaseActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.CacheModel;
import com.services.models.RequestModel;
import com.services.models.RequestType;
import com.utils.Utils;
import com.utils.exception.*;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

    public static final String DIR_CACHE = EnvironmentVariables.PERSISTENCE_PATH + "/";

    protected ArrayList<RequestModel> cola = new ArrayList<RequestModel>();

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

    public String connect(final String urlToExcecute, final String body, final String token, final HTTPMethod method) throws Exception {
        return connect(new RequestModel(urlToExcecute, body, token, method));
    }

    /**
     * Metodo a usar para la coneccion al servicio.
     * Manda por POST la peticion a la url con el body especifico y el token de segurdad en el header
     *
     * @return String con el formato JSON.
     * @throws Exception Si algo sale mal.
     */
    public String connect(final RequestModel req) throws Exception {
        CacheModel cache = checkCache(req.getUrlToExcecute());
        String resp = "";
        if (EnvironmentVariables.ONLINE) {//esta online
            if (req.getMethod() == HTTPMethod.GET) {
                if (cache == null) {
                    resp = connectToServer(req.getUrlToExcecute(), req.getBody(), req.getToken(), req.getMethod());//hay coneccion y no hay cache
                    saveResponse(req.getUrlToExcecute(), resp);
                    return resp;
                } else {//tengo cache
                    resp = cache.getRespuesta();
                    //verifico con el server
                    String check = validateInfo(req.getUrlToExcecute(), resp);
                    if (!check.isEmpty()) {
                        resp = check;
                        saveResponse(req.getUrlToExcecute(), resp);
                    }
                    return resp;
                }
            } else {
                return connectToServer(req.getUrlToExcecute(), req.getBody(), req.getToken(), req.getMethod());
            }
        } else {//no hay coneccion
            if (cache == null) {
                throw new NoCacheException();
            } else {//tengo cache
                return cache.getRespuesta();
            }
        }
    }

    public String validateInfo(String urlToExcecute, String resp) throws Exception {
        String sha = Utils.getSHA256(resp);
        String pathValidate = "http://" + ip + ":" + port + "/" + EnvironmentVariables.STARTPATH;
        return connectToServer(pathValidate + "configuracion/CHECK-SHA?url=" + urlToExcecute + "&sha=" + sha, null, TOKEN, HTTPMethod.GET);
    }

    public CacheModel checkCache(final String urlToExcecute) {
        CacheModel cache = null;
        try {
            FileInputStream fis = new FileInputStream(getFile(urlToExcecute));
            ObjectInputStream ois = new ObjectInputStream(fis);
            cache = (CacheModel) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cache;
    }

    protected void saveResponse(String urlToExcecute, String resp) {
        try {
            FileOutputStream fos = new FileOutputStream(getFile(urlToExcecute));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            CacheModel cache = new CacheModel(urlToExcecute, resp, true);
            oos.writeObject(cache);
            fos.close();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteCache() {
        try {
            File carpeta = new File(BaseActivity.getContext().getFilesDir(), EnvironmentVariables.PERSISTENCE_PATH);
            File files[] = carpeta.listFiles();
            for (File f : files) {
                FileInputStream fos = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fos);
                CacheModel cache = (CacheModel) ois.readObject();
                if (cache != null) {
                    if (cache.isVolatil()) {
                        f.delete();
                    }
                }
                fos.close();
                ois.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private File getFile(String urlToExcecute) throws IOException {
        File carpeta = new File(BaseActivity.getContext().getFilesDir(), EnvironmentVariables.PERSISTENCE_PATH);
        File fichero = new File(carpeta, Utils.getSHA256(urlToExcecute) + ".nfo");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        if (!fichero.exists()) {
            fichero.createNewFile();
        }
        return fichero;
    }

    public String connectToServer(final String urlToExcecute, final String body,
                                  final String token, HTTPMethod method) throws Exception {
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

    public ArrayList<RequestModel> getCola() {
        return cola;
    }

    public void addRequestToQueque(RequestModel req) {
        cola.add(req);
    }

    public void executeCola() {
        final String urlLlaves = "LLAVES";
        try {
            //lee las llaves
            FileInputStream fis = new FileInputStream(getFile(urlLlaves));
            ObjectInputStream ois = new ObjectInputStream(fis);
            CacheModel cache = (CacheModel) ois.readObject();
            fis.close();
            ois.close();
            HashMap<String, String> llaves = om.readValue(cache.getRespuesta(), HashMap.class);

            for (Iterator<RequestModel> iterator = cola.iterator(); iterator.hasNext(); ) {
                RequestModel req = iterator.next();
                updateRequest(llaves, req);
                if (req.getType() == RequestType.LOGIN) {
                    String token = connect(req);
                    llaves.put("TOKEN", token);
                } else if (req.getType() == RequestType.CREATE_ORDEN) {
                    String resp = connect(req);
                    llaves.put("###", resp);
                } else {
                    connect(req);
                }
                saveResponse(urlLlaves, om.writeValueAsString(llaves));
                iterator.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRequest(HashMap<String, String> llaves, RequestModel req) {
        for (String key : llaves.keySet()) {
            if (req.getBody() != null && req.getBody().contains(key)) {
                req.setBody(req.getBody().replace(key, llaves.get(key)));
            }
            if (req.getToken() != null && req.getToken().contains(key)) {
                req.setToken(req.getToken().replace(key, llaves.get(key)));
            }
            if (req.getUrlToExcecute() != null && req.getUrlToExcecute().contains(key)) {
                req.setUrlToExcecute(req.getUrlToExcecute().replace(key, llaves.get(key)));
            }
        }
    }
}

