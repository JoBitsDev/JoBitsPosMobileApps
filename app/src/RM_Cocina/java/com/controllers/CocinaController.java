package com.controllers;

import com.services.models.IpvRegistroModel;
import com.services.models.orden.ProductoVentaOrdenModel;
import com.services.web_connections.AlmacenWCS;
import com.services.web_connections.CocinaWCS;
import com.services.web_connections.NotificationWCS;

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

    String codCocina = "-";

    /**
     * Constructor de la clase.
     */
    public CocinaController(String codCocina) {
        almacenWCS = new AlmacenWCS();
    }

    public String getCodCocina() {
        return codCocina;
    }

    public void setCodCocina(String codCocina) {
        this.codCocina = codCocina;
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

    public List<IpvRegistroModel> getIPVRegistroExistencias(String codCocina) throws Exception {
        return almacenWCS.getIPVRegistroExistencias(codCocina);
    }

    public List<IpvRegistroModel> getIPVRegistroIPVS(String codCocina) throws Exception {
        return almacenWCS.getIPVRegistroIPVS(codCocina);
    }
}
