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
        this.codMesa = codMesa;
        this.codOrden = codOrden;
        this.usuarioTrabajador = usuarioTrabajador;
        path += P;
    }


    public OrdenWebConnectionService(String codMesa, String usuarioTrabajador) {
        super(EnvironmentVariables.IP, EnvironmentVariables.PORT);
        path += P;
        this.codMesa = codMesa;
        this.usuarioTrabajador = usuarioTrabajador;
    }

    public String fetchCodOrden() throws ServerErrorException, NoConnectionException {
        return codOrden = connect(path + fetchNoOrden);
    }

    public List<String> fetchAllCodOrden() throws ServerErrorException, NoConnectionException {
        return Arrays.asList(connect(path + "FINDALL_" + getCodMesa()).split(","));
    }


    public boolean initOrden() throws ServerErrorException, NoConnectionException {
        fetchCodOrden();
        return connect(path + "CREATE_" + codMesa + "_" + usuarioTrabajador).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean addProducto(String codProducto) throws ServerErrorException, NoConnectionException {
        return connect(path + "ADD_" + codOrden + "_" + codProducto).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean addProducto(String codProducto, float cantidad) throws ServerErrorException, NoConnectionException {
        return connect(path + "ADD_" + codOrden + "_" + codProducto + "_" + cantidad).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean removeProducto(String codProducto) throws ServerErrorException, NoConnectionException {
        return connect(path + "REMOVE_" + codOrden + "_" + codProducto).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean removeAllProducts() throws ServerErrorException, NoConnectionException {
        return connect(path + "REMOVEALL_" + codOrden).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean finishOrden(boolean deLaCasa) throws ServerErrorException, NoConnectionException {
        this.deLaCasa = deLaCasa;
        setDeLaCasa(deLaCasa);
        return connect(path + "FINISH_" + codOrden).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean setDeLaCasa(boolean resp) throws ServerErrorException, NoConnectionException {
        return connect(path + "SETDELACASA_" + codOrden + "_" + deLaCasa).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean sendToKitchen() throws ServerErrorException, NoConnectionException {
        return connect(path + "ENVIARCOCINA_" + codOrden).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public String findCodOrden() throws ServerErrorException, NoConnectionException {
        codOrden = connect(path + "FIND_" + codMesa);
        if (codOrden == null) {
            throw new NullPointerException("Error 101");//TODO: serio?? la mesera ve error 101, sabe lo que es, de paso abre el IDE y lo corrige.
        }
        return codOrden;
    }

    public OrdenModel findOrden(String codOrden) {
        return new OrdenXMLParser().fetch(path + codOrden).get(0);
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

    public String findCamareroUser() throws ServerErrorException, NoConnectionException {
        return connect(path + "GETCAMARERO_" + codOrden);
    }

    public boolean moverAMesa(String codMesa) throws ServerErrorException, NoConnectionException {
        return connect(path + "MOVERAMESA_" + getCodOrden() + "_" + codMesa).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean addNota(String pCod, String nota) throws ServerErrorException, NoConnectionException {
        return connect(path + "ADDNOTA_" + getCodOrden() + "_" + pCod + "_" + nota).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public String getNota(String pCod) throws ServerErrorException, NoConnectionException {
        String ret = connect(path + "GETNOTA_" + getCodOrden() + "_" + pCod);
        return ret.equals(EnvironmentVariables.PETITION_FALSE) ? "" : ret;
    }

    public String getComensal(String pCod) throws ServerErrorException, NoConnectionException {
        return connect(path + "GETCOMENSAL_" + getCodOrden() + "_" + pCod);
    }

    public boolean addComensal(String pCod, String comensal) throws ServerErrorException, NoConnectionException {
        return connect(path + "ADDCOMENSAL_" + getCodOrden() + "_" + pCod + "_" + comensal).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public String menuInfantil(int entrante, int plato_fuerte, int liquido, int postre, String menuinfantil_nota) throws ServerErrorException, NoConnectionException {
        return connect(path + "MENUINFANTIL_" + getCodOrden() + "_" + entrante + "_" + plato_fuerte + "_" + postre + "_" + liquido + "_" + menuinfantil_nota);
    }

    public boolean cederAUsuario(String usuario) throws ServerErrorException, NoConnectionException {
        return connect(path + "CEDERORDEN_" + getCodOrden() + "_" + usuario).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean isMine() throws ServerErrorException, NoConnectionException {
        return connect(path + "ISMINE_" + getCodMesa() + "_" + getUsuarioTrabajador()).equals(EnvironmentVariables.PETITION_TRUE);
    }

    public boolean validate() throws ServerErrorException, NoConnectionException {
        return connect(path + "ISVALID_" + getCodOrden()).equals(EnvironmentVariables.PETITION_TRUE);
    }
}
