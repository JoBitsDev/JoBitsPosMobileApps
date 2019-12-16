package com.main.webServerCon;


import com.main.Orden;
import com.main.parser.OrdenXMLParser;
import com.main.res;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Jorge on 24/9/17.
 */

public class ordenConn extends simpleConn {

    private String codOrden,usuarioTrabajador,codMesa;
    private ArrayList<String> codOrdenes = new ArrayList<String>();
    private final String P = "com.restmanager.orden/",
    fetchNoOrden =  "fetch";
    boolean deLaCasa = false;




    public ordenConn(String codOrden,String codMesa,String usuarioTrabajador) {
        super(res.IP, "8080");
        this.codMesa = codMesa;
        this.codOrden = codOrden;
        this.usuarioTrabajador = usuarioTrabajador;
        path+= P ;
    }


    public ordenConn(String codMesa,String usuarioTrabajador) throws ExecutionException, InterruptedException {
        super(res.IP, "8080");
        path+= P ;
        this.codMesa = codMesa;
        this.usuarioTrabajador = usuarioTrabajador;



    }

    public String fetchCodOrden() throws ExecutionException, InterruptedException {
        return codOrden =connect(path + fetchNoOrden);
    }



    public String getCodOrden() {
        return codOrden;
    }

    public void setCodOrden(String codOrden) {
        this.codOrden = codOrden;
    }

    public String getUsuarioTrabajador() {
        return usuarioTrabajador;
    }

    public void setUsuarioTrabajador(String usuarioTrabajador) {
        this.usuarioTrabajador = usuarioTrabajador;
    }

    public String getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(String codMesa) {
        this.codMesa = codMesa;
    }



}
