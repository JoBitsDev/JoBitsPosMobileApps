package com.services.web_connections;

import com.utils.EnvironmentVariables;

/**
 * Created by Jorge on 2/7/17.
 */
public class MesaWebConnectionService extends SimpleWebConnectionService {

    String user,codmesa;
    final String P = "com.restmanager.mesa/";
    final String fetchareas = "AREAS",
    fetchMesasFromArea = "AREA_";




    public MesaWebConnectionService(String user, String codMesa) {
        super(EnvironmentVariables.IP, "8080");
        this.user = user;
        this.codmesa = codMesa;
        path+= P;
    }


    public String[] getAreasName() {
        try{
        return connect(path + fetchareas).split(",");}catch(Exception e){

        }
        return new String[1];
    }
}