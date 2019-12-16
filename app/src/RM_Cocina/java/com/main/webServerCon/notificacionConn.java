package com.main.webServerCon;

import com.main.ProductovOrden;
import com.main.parser.ProductoVordenXMLParser;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 19/1/19.
 */

public class notificacionConn extends simpleConn {

    private ArrayList<String> codOrdenes = new ArrayList<String>();
    private final String P = "com.restmanager.notificacion/",
            notify = "NOTIFY_",
            fetchPendingOrders =  "PENDING_";


    private String codCocina;



    public notificacionConn(String codCocina) {
        super();
        this.codCocina = codCocina;
    }


    public List<ProductovOrden> fetchPendingOrders(){
        return new ProductoVordenXMLParser().fetch(path + P + fetchPendingOrders + codCocina);
    }

    public String notificar(ProductovOrden po){
        try {
            return connect(path + P + notify + po.getProductovOrdenPK().getOrdencodOrden() +"_"+ po.getProductoVenta().getPCod() );
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Error enviando la notificacion";

    }


}
