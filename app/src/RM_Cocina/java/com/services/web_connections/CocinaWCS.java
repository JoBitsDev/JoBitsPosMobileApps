package com.services.web_connections;


public class CocinaWCS extends SimpleWebConnectionService {

    private final String P = "cocina/",
            fetchNames = "NAMES";

    public CocinaWCS() {
        super();
        path += P;
    }

    public String[] getCocinasNames() throws Exception {
        String URL = path + fetchNames;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructArrayType(String.class));
    }
}
