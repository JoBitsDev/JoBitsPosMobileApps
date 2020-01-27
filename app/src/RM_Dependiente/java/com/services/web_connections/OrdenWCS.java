package com.services.web_connections;

import com.services.models.OrdenModel;
import com.utils.EnvironmentVariables;
import com.services.parsers.OrdenXMLParser;


import java.util.*;

/**
 * Created by Jorge on 24/9/17.
 */

public class OrdenWCS extends SimpleWebConnectionService {

    private String codOrden, usuarioTrabajador, codMesa;

    private final String P = "orden/",
            FETCH_NO_ORDEN = "FETCH",
            CREATE = "CREATE",
            ADD = "ADD",
            REMOVE = "REMOVE",
            FINISH = "FINISH",
            SET_DE_LA_CASA = "SET-DE-LA-CASA",
            ENVIAR_COCINA = "ENVIAR-COCINA",
            MOVER_MESA = "MOVER-MESA",
            ADD_NOTA = "ADD_NOTA",
            GET_NOTA = "GET_NOTA";

    boolean deLaCasa = false;

    public OrdenWCS(String codOrden, String codMesa) throws Exception {
        super();
        this.path += P;

        this.codMesa = codMesa;
        this.codOrden = codOrden;
        this.deLaCasa = findOrden(codOrden).getDeLaCasa();
    }


    public OrdenWCS(String codMesa) {
        super();
        this.path += P;

        this.codMesa = codMesa;
        this.deLaCasa = false;
    }

    public String fetchCodOrden() throws Exception {
        String resp = connect(path + FETCH_NO_ORDEN, null, super.TOKEN, HTTPMethod.GET);
        return this.codOrden = om.readValue(resp, om.getTypeFactory().constructArrayType(String.class));
    }

    public boolean initOrden() throws Exception {
        fetchCodOrden();
        connect(path + CREATE, this.codMesa, super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public boolean addProducto(String codProducto) throws Exception {
        return addProducto(codProducto, 1f);
    }

    public boolean addProducto(String codProducto, float cantidad) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("codOrden", this.codOrden);
        hm.put("codProducto", codProducto);
        hm.put("cantidad", cantidad);
        connect(path + ADD, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public boolean removeProducto(String codProducto) throws Exception {
        return removeProducto(codProducto, 1);
    }

    public boolean removeProducto(String codProducto, float cantidad) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("codOrden", this.codOrden);
        hm.put("codProducto", codProducto);
        hm.put("cantidad", cantidad);
        connect(path + REMOVE, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public boolean finishOrden() throws Exception {
        connect(path + FINISH, this.codOrden, super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public boolean setDeLaCasa(boolean deLaCasa) throws Exception {
        this.deLaCasa = deLaCasa;
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("codOrden", this.codOrden);
        hm.put("deLaCasa", this.deLaCasa);
        connect(path + SET_DE_LA_CASA, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public boolean sendToKitchen() throws Exception {
        connect(path + ENVIAR_COCINA, codOrden, super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public OrdenModel findOrden(String codOrden) throws Exception {
        String URL = path + "?codOrden=" + codOrden;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, OrdenModel.class);
    }

    public boolean moverAMesa(String codMesa) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("codOrden", this.codOrden);
        hm.put("codMesa", codMesa);
        connect(path + MOVER_MESA, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public boolean addNota(String pCod, String nota) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("codOrden", this.codOrden);
        hm.put("codProd", pCod);
        hm.put("nota", nota);
        connect(path + ADD_NOTA, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
        return true;
    }

    public String getNota(String pCod) throws Exception {
        String URL = path + GET_NOTA + "?codOrden=" + this.codOrden + "&codProd=" + pCod;
        String resp = connect(path + GET_NOTA, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructArrayType(String.class));
    }

    public String getComensal(String pCod) throws Exception {
        return connect(path + "GETCOMENSAL_" + getCodOrden() + "_" + pCod);
    }

    public boolean addComensal(String pCod, String comensal) throws Exception {
        return connect(path + "ADDCOMENSAL_" + getCodOrden() + "_" + pCod + "_" + comensal).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public String menuInfantil(int entrante, int plato_fuerte, int liquido, int postre, String menuinfantil_nota) throws Exception {
        return connect(path + "MENUINFANTIL_" + getCodOrden() + "_" + entrante + "_" + plato_fuerte + "_" + postre + "_" + liquido + "_" + menuinfantil_nota);
    }

    public boolean cederAUsuario(String usuario) throws Exception {
        return connect(path + "CEDERORDEN_" + getCodOrden() + "_" + usuario).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean isMine() throws Exception {
        return connect(path + "ISMINE_" + getCodMesa() + "_" + getUsuarioTrabajador()).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean validate() throws Exception {
        return connect(path + "ISVALID_" + getCodOrden()).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean isDeLaCasa() {
        return deLaCasa;
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

    public String getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(String codMesa) {
        this.codMesa = codMesa;
    }

}
