package com.services.web_connections;

import com.services.models.IpvRegistroModel;
import java.util.List;

/**
 * Capa: Services.
 * Esta clase es la encargada demanejar las peticiones del almacen con el servidor.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */
public class AlmacenWCS extends SimpleWebConnectionService {

    /**
     * Path local.
     */
    final String P = "almacen/";

    public AlmacenWCS() {
        super();
        path += P;
    }

    /**
     * Etiquetas a llamar.
     */
    final String REGISTRO_EXISTENCIAS = "REGISTRO-EXISTENCIAS",
            REGISTRO_IPVS = "REGISTRO-IPVS";

    public List<IpvRegistroModel> getIPVRegistroExistencias(String codCocina) throws Exception {
        String resp = connect(path + REGISTRO_EXISTENCIAS + "?ptoElab=" + codCocina, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, IpvRegistroModel.class));
    }

    public List<IpvRegistroModel> getIPVRegistroIPVS(String codCocina) throws Exception {
        String resp = connect(path + REGISTRO_IPVS + "?ptoElab=" + codCocina, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, IpvRegistroModel.class));
    }
}
