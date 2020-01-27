package com.services.web_connections;

import com.services.models.MesaModel;

import java.util.List;

/**
 * Created by Jorge on 2/7/17.
 */
public class AreaWCS extends SimpleWebConnectionService {

    final String P = "area/";

    /**
     * Etiquetas a llamar.
     */
    final String FIND_VACIAS = "FIND_VACIAS",
            FIND_ALL = "FIND-ALL",
            FIND_ALL_MESAS_AREA = "FIND-ALL-MESAS-AREA";

    public AreaWCS() {
        super();
        path += P;
    }

    public String[] getAreasName() throws Exception {
        String resp = connect(path, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructArrayType(String.class));
    }

    public String[] findVacias(String codMesa) throws Exception {
        String URL = path + FIND_VACIAS + "?codMesa=" + codMesa;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructArrayType(String.class));
    }

    public List<MesaModel> findMesas() throws Exception {
        String resp = connect(path + FIND_ALL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, MesaModel.class));
    }

    public List<MesaModel> findMesas(String selectedArea) throws Exception {
        String URL = path + FIND_ALL_MESAS_AREA + "?selectedArea=" + selectedArea;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, MesaModel.class));
    }

}
