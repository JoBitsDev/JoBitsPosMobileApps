package com.services.web_connections;




import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.utils.EnvironmentVariables.IP;


/**
 * Created by Jorge on 24/9/17.
 */

public class OrdenWebConnection extends SimpleWebConnectionService {

    private String codOrden,usuarioTrabajador,codMesa;
    private ArrayList<String> codOrdenes = new ArrayList<String>();
    private final String P = "com.restmanager.orden/",
    fetchNoOrden =  "fetch";
    boolean deLaCasa = false;




    public OrdenWebConnection(String codOrden, String codMesa, String usuarioTrabajador) {
        super(IP, "8080");
        this.codMesa = codMesa;
        this.codOrden = codOrden;
        this.usuarioTrabajador = usuarioTrabajador;
        path+= P ;
    }


    public OrdenWebConnection(String codMesa, String usuarioTrabajador) throws ExecutionException, InterruptedException {
        super(IP, "8080");
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
