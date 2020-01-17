package com.services.web_connections;

import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

/**
 * Capa: Services.
 * Copia de la CocinaWebConnection original para pedir los nombres de las cocinas.
 * @extens SimpleWebConnectionService ya que es un servicio.
 */

public class CocinaWebConnection extends SimpleWebConnectionService {

    private final String P = "com.restmanager.cocina/",
            fetchNames = "NAMES";

    public CocinaWebConnection() {
        super();
    }

    /**
     * Obtiene los nombres de todas las cocinas.
     *
     * @return Un arreglo de String con los nombres de todas las cocinas.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String[] getCocinasNames() throws Exception {
        return connect(path + P + fetchNames).split(",");
    }


}
