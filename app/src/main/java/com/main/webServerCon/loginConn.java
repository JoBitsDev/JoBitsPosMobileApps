package com.main.webServerCon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 24/9/17.
 */

public class loginConn extends simpleConn {


    private String user,pass;

    public loginConn(String ip, String port,String user,String pass) {
        super(ip, port);
        this.user = user;
        this.pass = pass;
    }

    public boolean authenticate () throws ExecutionException, InterruptedException {
           String r = connect(path+"com.restmanager.personal/l_"+user+"_"+pass);
            return r.equals("1");

    }




}
