package com.services.web_connections;

import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.List;

/**
 * Capa: Services.
 * Copia de la CocinaWCS original para pedir los nombres de las cocinas.
 *
 * @extens SimpleWebConnectionService ya que es un servicio.
 */

public class CocinaWCS extends SimpleWebConnectionService {

    private final String P = "cocina/",
            names = "NAMES";

    public CocinaWCS() {
        super();
        path += P;
    }

    /**
     * Obtiene los nombres de todas las cocinas.
     *
     * @return Un arreglo de String con los nombres de todas las cocinas.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String[] getCocinasNames() throws Exception {
        String resp = connect(path + names, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, String[].class/*om.getTypeFactory().constructCollectionType(List.class, String.class)*/);
    }

}
