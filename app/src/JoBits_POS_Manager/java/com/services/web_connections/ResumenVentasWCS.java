package com.services.web_connections;

import com.services.models.DetallesVentasModel;

import java.util.List;

public class ResumenVentasWCS extends SimpleWebConnectionService {

    private final String p = "venta/",
            RESUMEN = "SALES",
            DETALLES_POR_AREA = "DETALLES-POR-AREA",
            DETALLES_POR_DEPENDIENTE = "DETALLES-POR-DEPENDIENTE",
            DETALLES_POR_COCINA = "DETALLES-POR-COCINA",
            DETALLES_POR = "DETALLES-POR";

    public ResumenVentasWCS() {
        super();
        path += p;
    }

    public String getResumenVentas(String fecha) throws Exception {
        String URL = path + RESUMEN + "?fecha=" + fecha.replace("/", "-");
        return connect(URL, null, TOKEN, HTTPMethod.GET);
    }

    public List<DetallesVentasModel> getDetallesPorArea(String fecha, String areaCod) throws Exception {
        String URL = path + DETALLES_POR_AREA + "?fecha=" + fecha.replace("/", "-") + "&areaCod=" + areaCod;
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

    public List<DetallesVentasModel> getDetallesPorDependientes(String fecha, String usuario) throws Exception {
        String URL = path + DETALLES_POR_DEPENDIENTE + "?fecha=" + fecha.replace("/", "-") + "&usuario=" + usuario;
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

    public List<DetallesVentasModel> getDetallesPorCocina(String fecha, String cocinaCod) throws Exception {
        String URL = path + DETALLES_POR_COCINA + "?fecha=" + fecha.replace("/", "-") + "&cocinaCod=" + cocinaCod;
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

    public List<DetallesVentasModel> getDetallesPor(String fecha) throws Exception {
        String URL = path + DETALLES_POR + "?fecha=" + fecha.replace("/", "-");
        String resp = connect(URL, null, TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, DetallesVentasModel.class));
    }

}
