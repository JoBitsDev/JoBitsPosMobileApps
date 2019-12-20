package com.controllers;

import com.services.models.InsumoAlmacenModel;
import com.services.web_connections.AlmacenWebConnectionService;
import com.services.web_connections.CocinaWebConnection;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PantallaPrincipalController extends BaseController {

    AlmacenWebConnectionService connection;
    CocinaWebConnection cocinaWebConnection;

    public PantallaPrincipalController(String usuario) {
        connection = new AlmacenWebConnectionService(usuario, null);
    }

    public boolean imprimirTicketCompra() throws ExecutionException, InterruptedException {
        return connection.imprimirTicketCompra();
    }

    public boolean imprimirEstadoActual() throws ExecutionException, InterruptedException {
        return connection.imprimirEstadoAlmacen();
    }

    public void darEntrada(InsumoAlmacenModel i, float cantidad, float monto) throws ExecutionException, InterruptedException {
        connection.darEntrada(i, cantidad, monto);
    }

    public void darSalida(InsumoAlmacenModel i, float cantidad, String ipv) throws ExecutionException, InterruptedException {
        connection.darSalida(i, cantidad, ipv);
    }

    public void rebajar(InsumoAlmacenModel i, float cantidad, String toString) throws ExecutionException, InterruptedException {
        connection.rebajar(i, cantidad, toString);
    }

    public List<InsumoAlmacenModel> getPrimerAlmacen() throws ExecutionException, InterruptedException {
        return connection.getPrimerAlmacen();
    }

    public String[] getCocinasNamesForIPV(String insumoCod) throws ExecutionException, InterruptedException {
        return connection.getCocinasNamesForIPV(insumoCod);
    }

    public String[] getCocinasNames() {
        cocinaWebConnection = new CocinaWebConnection();
        return cocinaWebConnection.getCocinasNames();
    }

    public  List<InsumoAlmacenModel> filterBy(String codPtoElaboracion){
        return connection.filtrarBy(codPtoElaboracion);
    }
}
