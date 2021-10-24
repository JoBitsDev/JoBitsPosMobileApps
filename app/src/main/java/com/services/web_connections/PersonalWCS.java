package com.services.web_connections;

import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

/**
 * Capa: Services.
 * Esta clase es la encargada de gestionar el personal fuera que no sea login,
 * como devolver una lista de los usuarios activos en el sistema para ceder ordenes.
 * usuario y la contrasenna y proporciona un metodo para autenticarlos.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */

public class PersonalWCS extends RetrofitBaseConection {

    /**
     * Obtiene los usuarios activos en ese momento para poder ceder orden.
     *
     * @return Un arreglo de Strign con los usuarios activos.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String[] getPersonalOnline() throws Exception {//TODO: buscan endpoint que satisfaga esta solucitud
        String[] ret = {};
        return ret;
    }
}
