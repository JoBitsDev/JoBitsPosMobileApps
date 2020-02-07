package com.services.web_connections;


import java.util.HashMap;
import java.util.List;

public class NotificationWCS extends SimpleWebConnectionService {

    private final String P = "notificacion/",
            NOTIFY = "NOTIFY",
            FETCH_PENDING_ORDERS = "PENDING";

    public NotificationWCS() {
        super();
        path += P;
    }

    public List<ProductoVentaOrdenModel> fetchPendingOrders(String codCocina) throws Exception {
        String URL = path + FETCH_PENDING_ORDERS + "?codCocina=" + codCocina;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, ProductoVentaOrdenModel.class));
    }

    public String notificar(ProductoVentaOrdenModel po) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("codOrden", po.getProductoVentaOrdenPK().getOrdencodOrden());
        hm.put("codProducto", po.getProductoVenta().getPCod());
        String resp = connect(path + NOTIFY, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
        return om.readValue(resp, String.class);
    }

}
