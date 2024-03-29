package com.services.web_connections;

import com.activities.BaseActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.CacheModel;
import com.services.models.RequestModel;
import com.services.models.RequestType;
import com.services.models.orden.OrdenModel;
import com.utils.EnvironmentVariables;
import com.utils.Utils;
import com.utils.exception.NoCacheException;
import com.utils.exception.ServerErrorException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Capa: Services
 * Clase base para TODOS los servicios de la aplicación.
 * TODAS las activitys extienden de esta clase y proporciona metodos basicos para todas
 * como conectarse o los fetch.
 */

public class SimpleWebConnectionService {

    /**
     * Tiempo maximo esperado para la lectura.
     */
    public static final int MAX_READ_TIME = 5 * 1000;
    /**
     * Tiempo maximo esperado para la respuesta del servidor.
     */
    public static final int MAX_RESPONSE_TIME = 3 * 1000;
    public static final String DIR_CACHE = EnvironmentVariables.PERSISTENCE_PATH + "/";
    /**
     * Token para las llamandas seguras al servidor.
     */
    protected static String TOKEN = null;
    /**
     * Token para autenticarse en el servidor y que este lo redirija hacia la base de datos correspondiente
     */
    protected static String TENNANT_TOKEN = null;
    protected final ObjectMapper om = new ObjectMapper();
    private final String HTTP_HEADER_LOCATION = "Location";
    private final String HTTP_HEADER_TENNANT_ID = "TennantId ";
    private final String HTTP_HEADER_AUTHORITATION = "Authorization";
    private final String HTTP_HEADER_BEARER = "Bearer ";
    private final String queque = "QUEQUE";

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
        return connect(new RequestModel(urlToExcecute, body, TENNANT_TOKEN, token, method));
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
                    resp = connectToServer(req.getUrlToExcecute(), req.getBody(), req.getTennantToken(), req.getToken(), req.getMethod());//hay conexion y no hay cache
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
                return connectToServer(req.getUrlToExcecute(), req.getBody(), req.getTennantToken(), req.getToken(), req.getMethod());
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
        return connectToServer(pathValidate + "configuracion/CHECK-SHA?url=" + urlToExcecute + "&sha=" + sha, null, TENNANT_TOKEN, TOKEN, HTTPMethod.GET);
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
                                  final String tennantToken, final String token, HTTPMethod method) throws Exception {
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
        con.setRequestProperty(HTTP_HEADER_AUTHORITATION, HTTP_HEADER_BEARER + token);
        con.setRequestProperty(HTTP_HEADER_LOCATION, HTTP_HEADER_TENNANT_ID + tennantToken);

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

    public void addRequestToQueque(RequestModel req) throws IOException, ClassNotFoundException {
        CacheModel quequeCache = checkCache(queque);
        ArrayList<RequestModel> cola = new ArrayList<RequestModel>();
        if (quequeCache != null) {
            cola = om.readValue(quequeCache.getRespuesta(), om.getTypeFactory().constructCollectionType(List.class, RequestModel.class));
        }
        cola.add(req);

        saveResponse(queque, om.writeValueAsString(cola));
    }

    public boolean uploadQueque() throws Exception {//TODO: falta tratamiento con tennant offline
        final String urlLlaves = "LLAVES";
        //lee las llaves
        CacheModel llavesCache = checkCache(urlLlaves);
        HashMap<String, String> llaves = new HashMap<String, String>();
        if (llavesCache != null) {
            om.readValue(llavesCache.getRespuesta(), HashMap.class);
        }

        CacheModel quequeCache = checkCache(queque);
        ArrayList<RequestModel> cola = new ArrayList<RequestModel>();
        if (quequeCache != null) {
            cola = om.readValue(quequeCache.getRespuesta(), om.getTypeFactory().constructCollectionType(List.class, RequestModel.class));
        }
        if (cola.isEmpty()) {
            return false;
        }
        for (Iterator<RequestModel> iterator = cola.iterator(); iterator.hasNext(); ) {
            try {
                RequestModel req = iterator.next();
                llaves.put("TOKEN", TOKEN);
                llaves.put("TENNANT_TOKEN",TENNANT_TOKEN);
                updateRequest(llaves, req);
                if (req.getType() == RequestType.LOGIN) {
                    String token = connect(req);
                    TOKEN = token;
                } else if (req.getType() == RequestType.TENNANT) {
                    String resp = connect(req);
                    TENNANT_TOKEN = resp;
                } else if (req.getType() == RequestType.CREATE_ORDEN) {
                    String resp = connect(req);
                    OrdenModel orden = om.readValue(resp, OrdenModel.class);
                    llaves.put(req.getUid(), orden.getCodOrden());
                } else {
                    connect(req);
                }
            } catch (ServerErrorException e) {
                if (e.getCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    throw e;
                } else if (e.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    throw e;
                }
            }
            iterator.remove();
            saveResponse(queque, om.writeValueAsString(cola));
            saveResponse(urlLlaves, om.writeValueAsString(llaves));
        }
        return true;
    }

    private void updateRequest(HashMap<String, String> llaves, RequestModel req) {
        for (String key : llaves.keySet()) {
            if (req.getBody() != null && req.getBody().contains(key)) {
                req.setBody(req.getBody().replace(key, llaves.get(key)));
            }
            if (req.getToken() != null && req.getToken().contains(key)) {
                req.setToken(req.getToken().replace(key, llaves.get(key)));
            }
            if (req.getTennantToken() != null && req.getTennantToken().contains(key)) {
                req.setTennantToken(req.getTennantToken().replace(key, llaves.get(key)));
            }
            if (req.getUrlToExcecute() != null && req.getUrlToExcecute().contains(key)) {
                req.setUrlToExcecute(req.getUrlToExcecute().replace(key, llaves.get(key)));
            }
        }
    }
}

