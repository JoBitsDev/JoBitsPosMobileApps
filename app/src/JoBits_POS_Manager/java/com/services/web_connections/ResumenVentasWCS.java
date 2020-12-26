package com.services.web_connections;

import com.services.models.DetallesVentasModel;

import java.util.List;

public class ResumenVentasWCS extends SimpleWebConnectionService {

    private final String p = "venta/",
            RESUMEN = "SALES",
            RESUMEN_COUNT = "SALES-COUNT",
            DETALLES_POR_AREA = "DETALLES-POR-AREA",
            DETALLES_POR_DEPENDIENTE = "DETALLES-POR-DEPENDIENTE",
            DETALLES_POR_COCINA = "DETALLES-POR-COCINA",
            DETALLES_POR = "DETALLES-POR";


    public ResumenVentasWCS() {
        super();
        path += p;
    }

    public List<Integer> getResumenVentasCount(String fecha) throws Exception {
        String URL = path + RESUMEN_COUNT + "?fecha=" + fecha.replace("/", "-");
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, Integer.TYPE));
    }

    public String getResumenVentas(int idVenta) throws Exception {
        String URL = path + RESUMEN + "?idVenta=" + idVenta;
        return connect(URL, null, TOKEN, HTTPMethod.GET);
    }

    public List<DetallesVentasModel> getDetallesPorArea(int idVenta, String areaCod) throws Exception {
        String URL = path + DETALLES_POR_AREA + "?idVenta=" + idVenta + "&areaCod=" + areaCod;
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

    public List<DetallesVentasModel> getDetallesPorDependientes(int idVenta, String usuario) throws Exception {
        String URL = path + DETALLES_POR_DEPENDIENTE + "?idVenta=" + idVenta + "&usuario=" + usuario;
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

    public List<DetallesVentasModel> getDetallesPorCocina(int idVenta, String cocinaCod) throws Exception {
        String URL = path + DETALLES_POR_COCINA + "?idVenta=" + idVenta + "&cocinaCod=" + cocinaCod;
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

    public List<DetallesVentasModel> getDetallesPor(int idVenta) throws Exception {
        String URL = path + DETALLES_POR + "?idVenta=" + idVenta;
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

}
