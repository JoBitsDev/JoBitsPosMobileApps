package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.DetallesVentasModel;
import com.services.models.VentaResumenModel;
import com.services.web_connections.ResumenVentasWCS;

import java.util.List;

public class ResumenVentasController extends BaseController {
    private ResumenVentasWCS resumenVentasWCS = new ResumenVentasWCS();


    public List<Integer> getResumenVentasCount(String fecha) throws Exception {
        return resumenVentasWCS.getResumenVentasCount(fecha);

    }

    public VentaResumenModel getResumenVentas(int idVenta) throws Exception {
        String body = resumenVentasWCS.getResumenVentas(idVenta);
        return new ObjectMapper().readValue(body, VentaResumenModel.class);
    }

    public List<DetallesVentasModel> getDetallesPorArea(int idVenta, String areaCod) throws Exception {
        return resumenVentasWCS.getDetallesPorArea(idVenta, areaCod);
    }

    public List<DetallesVentasModel> getDetallesPorDependientes(int idVenta, String usuario) throws Exception {
        return resumenVentasWCS.getDetallesPorDependientes(idVenta, usuario);
    }

    public List<DetallesVentasModel> getDetallesPorCocina(int idVenta, String cocinaCod) throws Exception {
        return resumenVentasWCS.getDetallesPorCocina(idVenta, cocinaCod);
    }

    public List<DetallesVentasModel> getDetallesPor(int idVenta) throws Exception {
        return resumenVentasWCS.getDetallesPor(idVenta);
    }
}
