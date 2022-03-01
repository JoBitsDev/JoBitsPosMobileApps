package com.services.web_connections;


import com.services.models.orden.ProductoVentaOrdenModel;
import com.services.web_connections.interfaces.NotificationWCI;

import java.util.List;

public class NotificationWCS extends RetrofitBaseConection {

    private NotificationWCI service = retrofit.create(NotificationWCI.class);

    public NotificationWCS() {
        super();
    }

    public List<ProductoVentaOrdenModel> fetchPendingOrders(String codCocina) throws Exception {
        return handleResponse(service.getNotifications(TENNANT_TOKEN, getBearerToken(), codCocina).execute());
    }

    public String notificar(ProductoVentaOrdenModel po) throws Exception {
        return handleResponse(service.notifiOfCompletition(TENNANT_TOKEN,
                getBearerToken(), "", po.getId(), po.getCantidad()).execute());//TODO: fix
    }


}
