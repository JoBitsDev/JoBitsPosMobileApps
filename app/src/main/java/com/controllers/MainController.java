package com.controllers;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutionException;

public class MainController extends BaseController {

    public boolean checkConnection() {
        try {
            Check c = new Check();
            c.execute(URLCONN);
            return c.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected class Check extends AsyncTask<String, Void, Boolean> {

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
