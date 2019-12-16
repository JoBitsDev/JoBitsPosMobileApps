package com.main.webServerCon;

import com.main.res;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 2/7/17.
 */
public class mesaConn extends simpleConn {

    String user,codmesa;
    final String P = "com.restmanager.mesa/";




    public mesaConn(String user,String codMesa) {
        super(res.IP, "8080");
        this.user = user;
        this.codmesa = codMesa;
        path+= P;
    }




}
