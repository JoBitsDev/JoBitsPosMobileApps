package com.services.web_connections;

import com.services.models.IpvRegistroModel;
import com.services.web_connections.interfaces.AlmacenWCI;

import java.util.List;

/**
 * Capa: Services.
 * Esta clase es la encargada demanejar las peticiones del almacen con el servidor.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */
public class AlmacenWCS extends RetrofitBaseConection {


    private AlmacenWCI service = retrofit.create(AlmacenWCI.class);

    public AlmacenWCS() {
        super();
    }

    /**
     * Etiquetas a llamar.
     */

    public List<IpvRegistroModel> getIPVRegistroExistencias(String codCocina) throws Exception {
        return handleResponse(service.getIpvventa(TENNANT_TOKEN,getBearerToken(),codCocina).execute());//TODO;invertir
    }

    public List<IpvRegistroModel> getIPVRegistroIPVS(String codCocina) throws Exception {
    return handleResponse(service.getIpvRegistro(TENNANT_TOKEN,getBearerToken(),codCocina).execute());//TODO:invertir
    }


}
