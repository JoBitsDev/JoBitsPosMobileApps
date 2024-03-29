package com.services.web_connections;

import com.services.models.VentaId;
import com.services.models.orden.OrdenModel;
import com.services.web_connections.interfaces.OrdenWCI;
import com.utils.exception.ServerErrorException;

import retrofit2.Response;

/**
 * Created by Jorge on 24/9/17.
 */

public class OrdenWCS extends RetrofitBaseConection {

    boolean deLaCasa = false;
    private OrdenWCI service = retrofit.create(OrdenWCI.class);
    public static VentaId ventaId;
    private String codOrden, codMesa;

    public OrdenWCS(String codOrden, String codMesa) throws Exception {
        super();
        if (ventaId == null){
            ventaId = handleResponse(service.resolveAbierta(TENNANT_TOKEN,getBearerToken()).execute());
        }
        this.codMesa = codMesa;
        this.codOrden = codOrden;
        this.deLaCasa = findOrden(codOrden).getDeLaCasa();
    }


    public OrdenWCS(String codMesa) throws Exception{
        super();
        if (ventaId == null){
            ventaId = handleResponse(service.resolveAbierta(TENNANT_TOKEN,getBearerToken()).execute());
        }
        this.codMesa = codMesa;
        this.deLaCasa = false;
    }

    /*public String fetchCodOrden() throws Exception {
        String resp = connect(path + FETCH_NO_ORDEN, null, super.TOKEN, HTTPMethod.GET);
        return this.codOrden = om.readValue(resp, String.class);
    }*/

    public OrdenModel initOrden() throws Exception {
        OrdenModel ret = handleResponse(service.create(TENNANT_TOKEN, getBearerToken(),ventaId.getId(), codMesa).execute());
        codOrden = ret.getCodOrden();
        return ret;
    }

    public boolean finishOrden() throws Exception {
        OrdenModel orden = findOrden(codOrden);
        Response<?> ret = service.finish(TENNANT_TOKEN, getBearerToken(), codOrden, true,orden.getOrdenvalorMonetario(),0).execute();
        if (ret.isSuccessful()) {
            return true;
        } else {
            throw new ServerErrorException(ret.message(), ret.code());
        }
    }

    public OrdenModel addProducto(String codProducto, float cantidad) throws Exception {
        return handleResponse(service.addToOrden(TENNANT_TOKEN, getBearerToken(), codOrden, codProducto, cantidad).execute());
    }

    public OrdenModel removeProducto(int idProducto, float cantidad) throws Exception {
        return handleResponse(service.deleteFromOrden(TENNANT_TOKEN, getBearerToken(), codOrden, idProducto, cantidad).execute());
    }

    public boolean setDeLaCasa(boolean deLaCasa) throws Exception {
        handleResponse(service.setDeLaCasa(TENNANT_TOKEN, getBearerToken(), codOrden, deLaCasa).execute());
        return true;
    }

    public boolean sendToKitchen() throws Exception {
         handleResponse(service.enviarCocina(TENNANT_TOKEN, getBearerToken(), codOrden).execute());
         return true;
    }

    public OrdenModel findOrden(String codOrden) throws Exception {
        return handleResponse(service.find(TENNANT_TOKEN, getBearerToken(), codOrden).execute());

    }

    public void saveOrdenToCache(String ordenJson) throws Exception {
        //saveResponse(path + "?codOrden=" + codOrden, ordenJson);
    }

    public boolean moverAMesa(String codMesa) throws Exception {
        handleResponse(service.moverAMesa(TENNANT_TOKEN, getBearerToken(), codOrden, codMesa).execute());
        return true;
    }

    public void addNota(int pCod, String nota) throws Exception {
        handleResponse(service.addNota(TENNANT_TOKEN, getBearerToken(), codOrden, pCod, nota).execute());
    }

    public String getNota(int pCod) throws Exception {
        return handleResponse(service.getNota(TENNANT_TOKEN, getBearerToken(), codOrden, pCod).execute()).get("nota");
    }

    public boolean cederAUsuario(String usuario) throws Exception {
        return handleResponse(service.cederUsuario(TENNANT_TOKEN, getBearerToken(), codOrden, usuario).execute());

    }

    public OrdenModel validate() throws Exception {
        return handleResponse(service.validate(TENNANT_TOKEN, getBearerToken(), codOrden).execute());

    }


    public boolean isDeLaCasa() {
        return deLaCasa;
    }

    public String getCodOrden() {
        return codOrden;
    }

    public void setCodOrden(String codOrden) {
        this.codOrden = codOrden;
    }

    public String getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(String codMesa) {
        this.codMesa = codMesa;
    }
}
