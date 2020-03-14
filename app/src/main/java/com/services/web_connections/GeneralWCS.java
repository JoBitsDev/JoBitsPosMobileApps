package com.services.web_connections;

import java.util.HashMap;

/**
 * Created by Jorge on 2/8/18.
 */

public class GeneralWCS extends SimpleWebConnectionService {


    private String p = "general/";
    final String INFO = "INFO";

    public GeneralWCS() {
        super();
        path += p;
    }

    public HashMap<String, Object> readInfo() throws Exception {
        String resp = connect(path + INFO, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, HashMap.class);
    }

    public boolean uploadQueque() {
        return super.uploadQueque();
    }
}
