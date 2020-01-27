package com.services.web_connections;

/**
 * Created by Jorge on 2/7/17.
 */
public class AreaWCS extends SimpleWebConnectionService {

    final String P = "area/";

    /**
     * Etiquetas a llamar.
     */
    final String AREAS = "AREAS";

    public AreaWCS() {
        super();
        path += P;
    }

    public String[] getAreasName() throws Exception {
        String resp = connect(path, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructArrayType(String.class));
    }
}
