package com.services.web_connections;

import com.services.models.OrdenModel;
import com.services.parsers.OrdenXMLParser;
import com.utils.EnvironmentVariables;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 24/9/17.
 */

public class OrdenWebConnectionService extends SimpleWebConnectionService {

    private String codOrden,usuarioTrabajador,codMesa;
    private ArrayList<String> codOrdenes = new ArrayList<String>();
    private final String P = "com.restmanager.orden/",
    fetchNoOrden =  "fetch";
    boolean deLaCasa = false;




    public OrdenWebConnectionService(String codOrden, String codMesa, String usuarioTrabajador) {
        super(EnvironmentVariables.IP, "8080");
        this.codMesa = codMesa;
        this.codOrden = codOrden;
        this.usuarioTrabajador = usuarioTrabajador;
        path+= P ;
    }


    public OrdenWebConnectionService(String codMesa, String usuarioTrabajador) throws ExecutionException, InterruptedException {
        super(EnvironmentVariables.IP, "8080");
        path+= P ;
        this.codMesa = codMesa;
        this.usuarioTrabajador = usuarioTrabajador;



    }

    public String fetchCodOrden() throws ExecutionException, InterruptedException {
        return codOrden =connect(path + fetchNoOrden);
    }

    public List<String> fetchAllCodOrden() throws ExecutionException, InterruptedException {
       return Arrays.asList(connect(path + "FINDALL_"+getCodMesa()).split(","));
    }


    public boolean initOrden(){
        try {
            fetchCodOrden();
            return  connect(path+"CREATE_"+codMesa+"_"+usuarioTrabajador).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addProducto(String codProducto){

        try {
            return  connect(path+"ADD_"+codOrden+"_"+codProducto).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;

    }
    public boolean addProducto(String codProducto,float cantidad){

        try {
            return  connect(path+"ADD_"+codOrden+"_"+codProducto+"_"+cantidad).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;

    }
    public boolean removeProducto(String codProducto){
        try {
            return  connect(path+"REMOVE_"+codOrden+"_"+codProducto).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean removeAllProducts(){
        try {
            return  connect(path+"REMOVEALL_"+codOrden).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean finishOrden (boolean deLaCasa){
        try {
            this.deLaCasa = deLaCasa;
                    connect(path+"SETDELACASA_"+codOrden+"_"+deLaCasa);
            return  connect(path+"FINISH_"+codOrden).equals("1");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public String sendToKitchen(){
        try {
            return connect(path+"ENVIARCOCINA_"+codOrden);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String findCodOrden() {
        try {
            codOrden = connect(path+"FIND_"+codMesa);
            if(codOrden == null){
                throw new NullPointerException("Error 101");
            }
           return codOrden;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public OrdenModel findOrden(String codOrden){
        return new OrdenXMLParser().fetch(path+codOrden).get(0);

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


    public String findCamareroUser() {
        try{
     return connect(path + "GETCAMARERO_"+codOrden);}
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String moverAMesa(String codMesa) {
        try{
            return connect(path + "MOVERAMESA_"+getCodOrden()+"_"+codMesa);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String addNota(String pCod,String nota){
        try {
            return connect(path + "ADDNOTA_"+getCodOrden()+"_"+pCod+"_"+nota);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getNota(String pCod){
        try {
            String ret = connect(path + "GETNOTA_"+getCodOrden()+"_"+pCod);
                   return ret.equals("0") ? "" : ret;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getComensal(String pCod) {
        try {
            return connect(path + "GETCOMENSAL_"+getCodOrden()+"_"+pCod);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addComensal(String pCod,String comensal) {
        try {
            return connect(path + "ADDCOMENSAL_"+getCodOrden()+"_"+pCod+"_"+comensal);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String menuInfantil(int entrante, int plato_fuerte, int liquido, int postre, String menuinfantil_nota) {
        try {
            return connect(path + "MENUINFANTIL_"+getCodOrden()+"_"+entrante+"_"+plato_fuerte+"_"+postre+"_"+liquido+"_"+menuinfantil_nota);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String cederAUsuario(String usuario) {
        try {
            return connect(path + "CEDERORDEN_"+getCodOrden()+"_"+ usuario);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isMine(){

        try {
            return connect(path + "ISMINE_"+getCodMesa()+"_"+getUsuarioTrabajador()).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean validate() {
        try {
            return connect(path + "ISVALID_" + getCodOrden()).equals("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
