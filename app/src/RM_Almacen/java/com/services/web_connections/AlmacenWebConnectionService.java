package com.services.web_connections;

import com.services.models.InsumoAlmacenModel;
import com.services.parsers.InsumoAlmacenXMLParser;
import com.utils.EnvironmentVariables;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

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
            LISTA_IPV = "IPVS",
            FILTRAR_ = "FILTRAR_";
    String user, codAlmacen;

    public AlmacenWebConnectionService(String user, String codAlmacen) {
        super();
        this.user = user;
        this.codAlmacen = codAlmacen;
        path += P;
    }

    public List<InsumoAlmacenModel> getPrimerAlmacen() throws ServerErrorException, NoConnectionException {
        return new InsumoAlmacenXMLParser().fetch(path);
    }

    public String darEntrada(InsumoAlmacenModel i, float cantidad, float price) throws ServerErrorException, NoConnectionException {
        return connect(path
                + ENTRADA
                + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo() + "_"
                + cantidad + "_" + price);
    }

    public String darSalida(InsumoAlmacenModel i, float cantidad, String codPtoElaboracion) throws ServerErrorException, NoConnectionException {
        return connect(path
                + SALIDA
                + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo()
                + "_" + cantidad
                + "_" + codPtoElaboracion);
    }

    public String rebajar(InsumoAlmacenModel i, float cantidad, String razon) throws ServerErrorException, NoConnectionException {
        return connect(path
                + MERMA
                + "_" + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo()
                + "_" + cantidad
                + "_" + razon);
    }

    public String[] getCocinasNamesForIPV(String codInsumo) throws ServerErrorException, NoConnectionException {
        return connect(path + LISTA_IPV + "_" + codInsumo).split(",");
    }

    public List<InsumoAlmacenModel> filtrarBy(String codPtoElaboracion) {
        return new InsumoAlmacenXMLParser().fetch(path + FILTRAR_ + codPtoElaboracion);
    public boolean imprimirTicketCompra() throws ServerErrorException, NoConnectionException {
        return connect(path + TICKET_COMPRA).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean imprimirEstadoAlmacen() throws ServerErrorException, NoConnectionException {
        return connect(path + ESTADO_ALMACEN).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean imprimirEstadoAlmacen() {
        try {
            return connect(path + ESTADO_ALMACEN).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        }
}
