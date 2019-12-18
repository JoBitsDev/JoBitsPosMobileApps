package com.services.web_connections;


import java.util.concurrent.ExecutionException;

import static com.utils.EnvironmentVariables.IP;

/**
 * Created by Jorge on 2/7/17.
 */
public class MesaWebConnection extends SimpleWebConnectionService {

    String user,codmesa;
    final String P = "com.restmanager.mesa/";




    public MesaWebConnection(String user, String codMesa) {
        super(IP, "8080");
        this.user = user;
        this.codmesa = codMesa;
        path+= P;
    }




}
