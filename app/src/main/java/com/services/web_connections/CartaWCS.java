package com.services.web_connections;

import java.util.HashMap;

/**
 * Created by Jorge on 2/8/18.
 */

public class CartaWCS extends SimpleWebConnectionService {


    private String p = "carta/";
    final String INFO = "INFO";

    public CartaWCS() {
        super();
        path += p;
    }

    public HashMap<String,Object> readInfo() throws Exception {
        String resp = connect(path + INFO, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, HashMap.class);
    }


}
