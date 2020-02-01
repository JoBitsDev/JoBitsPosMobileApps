package com.controllers;

import com.services.models.ProductoVentaOrdenModel;
import com.services.web_connections.CartaWCS;
import com.services.web_connections.CocinaWCS;
import com.services.web_connections.NotificationWCS;
import com.utils.adapters.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de PantallaPrincipalActivity para el POS Cocina, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller.
 */
public class CocinaController extends BaseController {

    public String notificar(ProductoVentaOrdenModel po) throws Exception {
        return new NotificationWCS().notificar(po);
    }

    public String[] getCocinasNames() throws Exception {
        return new CocinaWCS().getCocinasNames();
    }

    public List<ProductoVentaOrdenModel> fetchPendingOrders(String cocinaTrabajo) throws Exception {
        return new NotificationWCS().fetchPendingOrders(cocinaTrabajo);
    }

    public String getNombreRest() throws Exception {
        return new CartaWCS().getNombreRest();
    }
}
