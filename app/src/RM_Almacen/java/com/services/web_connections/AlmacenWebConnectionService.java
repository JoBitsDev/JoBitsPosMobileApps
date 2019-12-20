package com.services.web_connections;

import com.services.models.InsumoAlmacenModel;
import com.services.parsers.InsumoAlmacenXMLParser;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 2/7/17.
 */
public class AlmacenWebConnectionService extends SimpleWebConnectionService {

    final String P = "com.restmanager.almacen/";
    final String ENTRADA = "ENTRADA_",
            TICKET_COMPRA = "IMPRIMIR_TICKET_COMPRA",
            ESTADO_ALMACEN = "IMPRIMIR_ESTADO_ALMACEN",
            SALIDA = "SALIDA_",
            MERMA = "MERMAR",
            LISTA_IPV = "IPVS";
    String user, codAlmacen;


    public AlmacenWebConnectionService(String user, String codAlmacen) {
        super();
        this.user = user;
        this.codAlmacen = codAlmacen;
        path += P;
    }


    public List<InsumoAlmacenModel> getPrimerAlmacen() {
        return new InsumoAlmacenXMLParser().fetch(path);
    }

    public String darEntrada(InsumoAlmacenModel i, float cantidad, float price) throws ExecutionException, InterruptedException {
        return connect(path
                + ENTRADA
                + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo() + "_"
                + cantidad + "_" + price);
    }

    public String darSalida(InsumoAlmacenModel i, float cantidad, String codPtoElaboracion) throws ExecutionException, InterruptedException {
        return connect(path
                + SALIDA
                + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo()
                + "_" + cantidad
                + "_" + codPtoElaboracion);


    }

    public String rebajar(InsumoAlmacenModel i, float cantidad, String razon) throws ExecutionException, InterruptedException {
        return connect(path
                + MERMA
                + "_" + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo()
                + "_" + cantidad
                + "_" + razon);
    }


    public String[] getCocinasNamesForIPV(String codInsumo) throws ExecutionException, InterruptedException {
        String result = connect(path + LISTA_IPV + "_" + codInsumo);
        return result == null ? new String[0] : result.split(",");

    }

    public boolean imprimirTicketCompra() throws ExecutionException, InterruptedException {
        return connect(path + TICKET_COMPRA).equals("1");
    }

    public boolean imprimirEstadoAlmacen() throws ExecutionException, InterruptedException {
        return connect(path + ESTADO_ALMACEN).equals("1");

    }
}
