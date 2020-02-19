package com.controllers;

import android.content.Context;

import com.services.models.IpvRegistroModel;
import com.services.models.ProductoVentaOrdenModel;
import com.services.web_connections.AlmacenWCS;
import com.services.web_connections.CocinaWCS;
import com.services.web_connections.NotificationWCS;
import com.utils.adapters.IPVsAdapter;
import com.utils.adapters.MenuAdapter;

import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de PantallaPrincipalActivity para el POS Cocina, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller.
 */
public class CocinaController extends BaseController {

    /**
     * WCS del almacen.
     */
    AlmacenWCS almacenWCS;

    /**
     * Constructor de la clase.
     */
    public CocinaController() {
        almacenWCS = new AlmacenWCS(null);
    }

    public String notificar(ProductoVentaOrdenModel po) throws Exception {
        return new NotificationWCS().notificar(po);
    }

    public String[] getCocinasNames() throws Exception {
        return new CocinaWCS().getCocinasNames();
    }

    public List<ProductoVentaOrdenModel> fetchPendingOrders(String cocinaTrabajo) throws Exception {
        return new NotificationWCS().fetchPendingOrders(cocinaTrabajo);
    }

    public IPVsAdapter getIPVAdapter(Context c, int ipvRegisro) throws Exception {
        return new IPVsAdapter(c, ipvRegisro, almacenWCS.getIPVRegistro(""));
    }

    public IPVsAdapter getIPVAdapter(Context c, int ipvRegisro, String codCocina) throws Exception {
        return new IPVsAdapter(c, ipvRegisro, almacenWCS.getIPVRegistro(codCocina));
    }

    public List<IpvRegistroModel> getIPVRegistro(String codCocina) throws Exception {
        return almacenWCS.getIPVRegistro(codCocina);
    }
}
