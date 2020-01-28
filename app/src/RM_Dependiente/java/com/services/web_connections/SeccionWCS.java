package com.services.web_connections;

import com.services.models.SeccionModel;

import java.util.List;

/**
 * Created by Jorge on 2/7/17.
 */
public class SeccionWCS extends SimpleWebConnectionService {

    final String P = "seccion/";

    public SeccionWCS() {
        super();
        path += P;
    }

    public List<SeccionModel> getSecciones() throws Exception {
        String resp = connect(path, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, SeccionModel.class));
    }
}
