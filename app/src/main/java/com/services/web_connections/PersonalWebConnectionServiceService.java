package com.services.web_connections;

import com.utils.exception.*;

import java.util.concurrent.ExecutionException;

/**
 * Capa: Services.
 * Esta clase es la encargada de gestionar el personal fuera que no sea login,
 * como devolver una lista de los usuarios activos en el sistema para ceder ordenes.
 * usuario y la contrasenna y proporciona un metodo para autenticarlos.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */

public class PersonalWebConnectionServiceService extends SimpleWebConnectionService {

    /**
     * Path a la peticion.
     */
    private String P = "com.restmanager.personal/";

    /**
     * Peticion al servidor
     */
    private String usuariosActivos = "MOSTRAR_PERSONAL_TRABAJANDO";

    public PersonalWebConnectionServiceService() {
        super();
        path += P;
    }

    /**
     * Obtiene los usuarios activos en ese momento para poder ceder orden.
     *
     * @return Un arreglo de Strign con los usuarios activos.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String[] getUsuariosActivos() throws Exception {
        String[] result = {};
        String ret = connect(path + usuariosActivos);

        if (ret != null)
            result = ret.substring(0, ret.length() - 1).split(",");

        return result;
    }
}
