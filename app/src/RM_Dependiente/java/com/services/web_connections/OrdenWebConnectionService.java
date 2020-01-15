package com.services.web_connections;

import com.utils.exception.*;
import com.services.models.OrdenModel;
import com.utils.EnvironmentVariables;
import com.services.parsers.OrdenXMLParser;


import java.util.*;

/**
 * Created by Jorge on 24/9/17.
 */

public class OrdenWebConnectionService extends SimpleWebConnectionService {

    private String codOrden, usuarioTrabajador, codMesa;
    private ArrayList<String> codOrdenes = new ArrayList<String>();
    private final String P = "com.restmanager.orden/",
            fetchNoOrden = "fetch";
    boolean deLaCasa = false;


    public OrdenWebConnectionService(String codOrden, String codMesa, String usuarioTrabajador) {
        super(EnvironmentVariables.IP, EnvironmentVariables.PORT);
        this.path += P;

        this.codMesa = codMesa;
        this.codOrden = codOrden;
        this.usuarioTrabajador = usuarioTrabajador;
        this.deLaCasa = findOrden(codOrden).getDeLaCasa();
    }


    public OrdenWebConnectionService(String codMesa, String usuarioTrabajador) {
        super(EnvironmentVariables.IP, EnvironmentVariables.PORT);
        this.path += P;

        this.codMesa = codMesa;
        this.usuarioTrabajador = usuarioTrabajador;
        this.deLaCasa = false;
    }

    public String fetchCodOrden() throws Exception {
        return codOrden = connect(path + fetchNoOrden);
    }

    public List<String> fetchAllCodOrden() throws Exception {
        return Arrays.asList(connect(path + "FINDALL_" + getCodMesa()).split(","));
    }

    public boolean initOrden() throws Exception {
        fetchCodOrden();
        return connect(path + "CREATE_" + codMesa + "_" + usuarioTrabajador).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean addProducto(String codProducto) throws Exception {
        return connect(path + "ADD_" + codOrden + "_" + codProducto).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean addProducto(String codProducto, float cantidad) throws Exception {
        return connect(path + "ADD_" + codOrden + "_" + codProducto + "_" + cantidad).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean removeProducto(String codProducto) throws Exception {
        return connect(path + "REMOVE_" + codOrden + "_" + codProducto).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean removeAllProducts() throws Exception {
        return connect(path + "REMOVEALL_" + codOrden).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean finishOrden(boolean deLaCasa) throws Exception {
        this.deLaCasa = deLaCasa;
        setDeLaCasa(deLaCasa);
        return connect(path + "FINISH_" + codOrden).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean setDeLaCasa(boolean resp) throws Exception {
        deLaCasa = resp;
        return connect(path + "SETDELACASA_" + codOrden + "_" + deLaCasa).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean sendToKitchen() throws Exception {
        return connect(path + "ENVIARCOCINA_" + codOrden).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public String findCodOrden() throws Exception {
        codOrden = connect(path + "FIND_" + codMesa);
        if (codOrden == null) {
            throw new NullPointerException("Error 101");//TODO: serio?? la mesera ve error 101, sabe lo que es, de paso abre el IDE y lo corrige.
        }
        return codOrden;
    }

    public OrdenModel findOrden(String codOrden) {
        return new OrdenXMLParser().fetch(path + codOrden).get(0);
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

    public void setUsuarioTrabajador(String usuarioTrabajador) {
        this.usuarioTrabajador = usuarioTrabajador;
    }

    public String getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(String codMesa) {
        this.codMesa = codMesa;
    }

    public String findCamareroUser() throws Exception {
        return connect(path + "GETCAMARERO_" + codOrden);
    }

    public boolean moverAMesa(String codMesa) throws Exception {
        return connect(path + "MOVERAMESA_" + getCodOrden() + "_" + codMesa).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean addNota(String pCod, String nota) throws Exception {
        return connect(path + "ADDNOTA_" + getCodOrden() + "_" + pCod + "_" + nota).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public String getNota(String pCod) throws Exception {
        String ret = connect(path + "GETNOTA_" + getCodOrden() + "_" + pCod);
        return ret.equals(EnvironmentVariables.PETITION_FALSE) ? "" : ret;
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
}
