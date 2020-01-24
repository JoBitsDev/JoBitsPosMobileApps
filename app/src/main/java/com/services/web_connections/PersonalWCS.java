package com.services.web_connections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.exception.*;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Capa: Services.
 * Esta clase es la encargada de gestionar el personal fuera que no sea login,
 * como devolver una lista de los usuarios activos en el sistema para ceder ordenes.
 * usuario y la contrasenna y proporciona un metodo para autenticarlos.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */

public class PersonalWCS extends SimpleWebConnectionService {

    /**
     * Path a la peticion.
     */
    private String P = "login/";

    /**
     * Peticion al servidor
     */
    private String usuariosActivos = "MOSTRAR_PERSONAL_TRABAJANDO";

    public PersonalWCS() {
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
        String URL = path + usuariosActivos;
        String ret = connect(URL, null, super.TOKEN, HTTPMethod.POST);
        List<String> list = om.readValue(ret, om.getTypeFactory().constructCollectionType(List.class, String.class));
        return (String[]) list.toArray();
    }
}
