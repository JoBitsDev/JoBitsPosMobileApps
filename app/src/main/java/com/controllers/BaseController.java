package com.controllers;

import android.os.AsyncTask;

import com.utils.EnvironmentVariables;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public abstract class BaseController {

    protected final String URLCONN = "http://" + EnvironmentVariables.IP + ":" + EnvironmentVariables.PORT + "/RM";

    public boolean checkConnection(){
        try {
            Check c = new Check();
            c.execute(URLCONN);
            return c.get();
        } catch (Exception e) {
            return false;
        }
    }

    private class Check extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... uri) {
            URL url = null;
            try {
                url = new URL(uri[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                // Starts the query
                con.setConnectTimeout(400);
                con.connect();
                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }//TODO: arreglar esto que no pincha bien

    }
}
