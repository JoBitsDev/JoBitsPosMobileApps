package com.main.webServerCon;

import com.main.res;

/**
 * Created by Jorge on 2/7/17.
 */
public class areaConn extends simpleConn {

    String user,codmesa;
    final String P = "com.restmanager.area/";




    public areaConn(String user, String codMesa) {
        super(res.IP, "8080");
        this.user = user;
        this.codmesa = codMesa;
        path+= P;
    }




}
