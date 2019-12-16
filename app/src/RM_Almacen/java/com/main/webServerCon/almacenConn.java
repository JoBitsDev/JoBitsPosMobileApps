package com.main.webServerCon;

import com.main.objetos.InsumoAlmacen;
import com.main.parser.InsumoAlmacenXMLParser;
import com.main.res;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 2/7/17.
 */
public class almacenConn extends simpleConn {

    final String P = "com.restmanager.almacen/";
    final String ENTRADA = "ENTRADA_",
                TICKET_COMPRA = "IMPRIMIR_TICKET_COMPRA",
                ESTADO_ALMACEN = "IMPRIMIR_ESTADO_ALMACEN",
                SALIDA = "SALIDA_",
                MERMA = "MERMAR",
                LISTA_IPV = "IPVS";
    String user, codAlmacen;


    public almacenConn(String user, String codAlmacen) {
        super(res.IP, "8080");
        this.user = user;
        this.codAlmacen = codAlmacen;
        path += P;
    }


    public List<InsumoAlmacen> getPrimerAlmacen() {
        return new InsumoAlmacenXMLParser().fetch(path);
    }

    public String darEntrada(InsumoAlmacen i, float cantidad,float price) {
        try {
            return connect(path
                    + ENTRADA
                    + i.getInsumoAlmacenPK().getAlmacencodAlmacen()
                    + "_" + i.getInsumoAlmacenPK().getInsumocodInsumo() + "_"
                    + cantidad +"_"+ price);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String darSalida(InsumoAlmacen i, float cantidad,String codPtoElaboracion) {
        try {
            return connect(path
                    + SALIDA
                    + i.getInsumoAlmacenPK().getAlmacencodAlmacen()
                    + "_" + i.getInsumoAlmacenPK().getInsumocodInsumo()
                    + "_" + cantidad
                    + "_" + codPtoElaboracion);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String rebajar(InsumoAlmacen i , float cantidad, String razon){
        try {
            return connect(path
                    + MERMA
                    + "_" + i.getInsumoAlmacenPK().getAlmacencodAlmacen()
                    + "_" + i.getInsumoAlmacenPK().getInsumocodInsumo()
                    + "_" + cantidad
                    + "_" + razon);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }


    public String[] getCocinasNamesForIPV(String codInsumo) {
        try {
            String result = connect(path + LISTA_IPV + "_" + codInsumo);
            return result == null ? new String[0] : result.split(",");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public boolean imprimirTicketCompra() {
        try {
            return connect(path + TICKET_COMPRA).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
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
