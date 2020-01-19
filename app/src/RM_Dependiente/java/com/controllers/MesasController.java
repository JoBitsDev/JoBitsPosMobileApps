package com.controllers;

import android.app.Activity;

import com.activities.R;
import com.services.models.MesaModel;
import com.services.models.ProductoVentaModel;
import com.services.models.ProductoVentaOrdenModel;
import com.services.models.SeccionModel;
import com.services.parsers.MesaXMlParser;
import com.services.parsers.ProductoVentaOrdenXMLParser;
import com.services.parsers.ProductoVentaXMlParser;
import com.services.parsers.SeccionXMlParser;
import com.services.web_connections.CartaWebConnectionService;
import com.services.web_connections.MesaWebConnectionService;
import com.services.web_connections.OrdenWebConnectionService;
import com.utils.EnvironmentVariables;
import com.utils.adapters.MesaAdapter;
import com.utils.adapters.ProductoVentaOrdenAdapter;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.Collections;
import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de MesaActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller aplicacion.
 */
public class MesasController extends BaseController {

    private static final String urlMesas = "http://" + EnvironmentVariables.getIP() + ":" + EnvironmentVariables.getPORT() + "/" + EnvironmentVariables.STARTPATH + "com.restmanager.mesa";
    private String user;
    private OrdenWebConnectionService ordenWCService = null;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public MesaAdapter getData(String selectedArea, Activity act) {
        List<MesaModel> mesaModels;
        if (selectedArea != null) {
            mesaModels = new MesaXMlParser().fetch(urlMesas + "/AREA_" + selectedArea);
        } else {
            mesaModels = new MesaXMlParser().fetch(urlMesas);
        }
        MesaAdapter adaptador = new MesaAdapter(act, R.id.listaMesas, mesaModels, user);
        return adaptador;
    }

    public void starService(String codMesa) {
        ordenWCService = new OrdenWebConnectionService(codMesa, user);
    }

    public void setCodOrden(String cod_orden) {
        ordenWCService.setCodOrden(cod_orden);
    }

    public boolean validate() throws Exception {
        return ordenWCService.validate();
    }

    public String getNombreRest() {
        return new CartaWebConnectionService().getNombreRest();
    }

    public String[] getAreas() throws Exception {
        return new MesaWebConnectionService(user, null).getAreasName();
    }
}
