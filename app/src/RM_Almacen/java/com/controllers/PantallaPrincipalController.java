package com.controllers;

import com.services.models.InsumoAlmacenModel;
import com.services.web_connections.AlmacenWebConnectionService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PantallaPrincipalController extends BaseController {

    AlmacenWebConnectionService connection;

    public PantallaPrincipalController(String usuario) {
        connection = new AlmacenWebConnectionService(usuario, null);
    }

    public boolean imprimirTicketCompra(){
        return connection.imprimirTicketCompra();
    }

    public boolean imprimirEstadoActual() {
        return connection.imprimirEstadoAlmacen();
    }

    public void darEntrada(InsumoAlmacenModel i, float cantidad, float monto) throws Exception {
        if (super.checkConnection()) {
            connection.darEntrada(i, cantidad, monto);
        } else {
            throw new Exception();
        }
    }

    public void darSalida(InsumoAlmacenModel i, float cantidad, String ipv) throws Exception {
        if (super.checkConnection()) {
            connection.darSalida(i, cantidad, ipv);
        } else {
            throw new Exception();
        }
    }

    public void rebajar(InsumoAlmacenModel i, float cantidad, String toString) throws Exception {
        if (super.checkConnection()) {
            connection.rebajar(i, cantidad, toString);
        } else {
            throw new Exception();
        }
    }

    public List<InsumoAlmacenModel> getPrimerAlmacen() throws Exception {
        if (super.checkConnection()) {
            return connection.getPrimerAlmacen();
        } else {
            throw new Exception();
        }
    }

    public String[] getCocinasNamesForIPV(String insumoCod) throws Exception {
        if (super.checkConnection()) {
            return connection.getCocinasNamesForIPV(insumoCod);
        } else {
            throw new Exception();
        }
    }
}
