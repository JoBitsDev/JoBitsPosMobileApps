package com.controllers;

import android.app.Activity;

import com.activities.R;
import com.services.models.MesaModel;
import com.services.models.OrdenModel;
import com.services.web_connections.AreaWCS;
import com.services.web_connections.OrdenWCS;
import com.utils.adapters.MesaAdapter;

import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de MesaActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller aplicacion.
 */
public class MesasController extends BaseController {


    private static String ESTADO_MESA_VACIA = "vacia";
    private String user;
    private OrdenWCS ordenWCService = null;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public MesaAdapter getData(String selectedArea, Activity act) throws Exception {
        List<MesaModel> mesaModels;
        if (selectedArea == null) {
            mesaModels = new AreaWCS().findMesas();//    MesaXMlParser().fetch(urlMesas + "/AREA_" + selectedArea);
        } else {
            mesaModels = new AreaWCS().findMesas(selectedArea);//new MesaXMlParser().fetch(urlMesas);
        }
        MesaAdapter adaptador = new MesaAdapter(act, R.id.listaMesas, mesaModels, user);
        return adaptador;
    }

    public void initOrdenEnMesa(OrdenModel orden, String mesa) throws Exception {
        List<MesaModel> mesaModels = new AreaWCS().findMesas();
        for (MesaModel m : mesaModels) {
            if (m.getCodMesa().matches(mesa)) {
                m.setEstado(orden.getCodOrden() + " " + user);
                break;
            }
        }
        new AreaWCS().saveMesasList(mesaModels);
    }

    public void terminarOrdenEnMesa(String mesa) throws Exception{
        List<MesaModel> mesaModels = new AreaWCS().findMesas();
        for (MesaModel m : mesaModels) {
            if (m.getCodMesa().matches(mesa)) {
                m.setEstado(ESTADO_MESA_VACIA);
                break;
            }
        }
        new AreaWCS().saveMesasList(mesaModels);
    }

    public void starService(String codMesa) {
        ordenWCService = new OrdenWCS(codMesa);
    }

    public void setCodOrden(String cod_orden) {
        ordenWCService.setCodOrden(cod_orden);
    }

    public boolean validate() throws Exception {
        return ordenWCService.validate();
    }

    public String[] getAreas() throws Exception {
        return new AreaWCS().getAreasName();
    }
}
