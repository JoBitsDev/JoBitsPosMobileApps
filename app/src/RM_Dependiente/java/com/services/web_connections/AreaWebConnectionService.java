package com.services.web_connections;

import com.utils.EnvironmentVariables;

/**
 * Created by Jorge on 2/7/17.
 */
public class AreaWebConnectionService extends SimpleWebConnectionService {

    String user,codmesa;
    final String P = "com.restmanager.area/";




    public AreaWebConnectionService(String user, String codMesa) {
        super(EnvironmentVariables.IP, "8080");
        this.user = user;
        this.codmesa = codMesa;
        path+= P;
    }




}
