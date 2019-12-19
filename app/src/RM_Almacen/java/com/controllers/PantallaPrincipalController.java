package com.controllers;

import com.services.models.InsumoAlmacenModel;
import com.services.web_connections.AlmacenWebConnectionService;

import java.util.List;

public class PantallaPrincipalController extends BaseController {

    AlmacenWebConnectionService connection;

    public PantallaPrincipalController(String usuario) {
        connection = new AlmacenWebConnectionService(usuario, null);
    }

    public boolean imprimirTicketCompra() {

        return connection.imprimirTicketCompra();
    }

    public boolean imprimirEstadoActual() {

        return connection.imprimirEstadoAlmacen();
    }


    public void darEntrada(InsumoAlmacenModel i, float cantidad, float monto) {
        connection.darEntrada(i, cantidad, monto);
    }

    public void darSalida(InsumoAlmacenModel i, float cantidad, String ipv) {
        connection.darSalida(i, cantidad, ipv);
    }

    public void rebajar(InsumoAlmacenModel i, float cantidad, String toString) {
        connection.rebajar(i, cantidad, toString);
    }

    public List<InsumoAlmacenModel> getPrimerAlmacen() {
        return connection.getPrimerAlmacen();
    }

    public String[] getCocinasNamesForIPV(String insumoCod) {
        return connection.getCocinasNamesForIPV(insumoCod);
    }
}
