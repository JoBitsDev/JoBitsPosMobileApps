package com.services.web_connections;

import android.os.AsyncTask;

import com.utils.EnvironmentVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * Created by Jorge on 24/9/17.
 */

public class SimpleWebConnectionService {
    protected String
            ip,
            port,
            path,
            resp = null;

    protected HttpURLConnection con = null;
    protected URL url = null;


    public SimpleWebConnectionService(String ip, String port) {
        this.ip = ip;
        this.port = port;
        path = "http://" + ip + ":" + port + "/"+EnvironmentVariables.STARTPATH;

    }

    public SimpleWebConnectionService() {
        this.ip = EnvironmentVariables.IP;
        port = EnvironmentVariables.PORT;
        path = "http://" + ip + ":" + port + "/"+EnvironmentVariables.STARTPATH;
    }

    /**
     * este metodo devuelve la informacion de la consulta a la URL pasada por parametro
     *
     * @param url la URL a consultar
     * @return la respuesta de la consulta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String connect(String url) throws ExecutionException, InterruptedException {
        fetchData f = new fetchData();
        f.execute(url);
        return f.get();
    }

    protected class fetchData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            try {
                String ret = downloadUrl(url[0]);
                return ret;
            } catch (IOException e) {
                e.printStackTrace();
                return "0";
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

