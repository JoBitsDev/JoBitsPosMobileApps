package com.services.web_connections;

import com.services.models.ProductoVentaOrdenModel;
import com.services.parsers.ProductoVentaOrdenXMLParser;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 19/1/19.
 */

public class NotificationWCS extends SimpleWebConnectionService {

    private ArrayList<String> codOrdenes = new ArrayList<String>();
    private final String P = "com.restmanager.notificacion/",
            notify = "NOTIFY_",
            fetchPendingOrders =  "PENDING_";

    public NotificationWCS() {
        super();
    }


    public List<ProductoVentaOrdenModel> fetchPendingOrders(String codCocina){
        return new ProductoVentaOrdenXMLParser().fetch(path + P + fetchPendingOrders + codCocina);
    }

    public String notificar(ProductoVentaOrdenModel po){
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
