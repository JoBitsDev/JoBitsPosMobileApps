package com.services.web_connections;

import com.services.models.DetallesVentasModel;
import com.services.models.VentaResumenModel;
import com.services.web_connections.interfaces.ResumenVentasWCI;

import java.util.List;

import retrofit2.Response;

public class ResumenVentasWCS extends RetrofitBaseConection {

    public List<Integer> getResumenVentasCount(int[] fecha) throws Exception {
        Response<List<Integer>> resp = retrofit.create(ResumenVentasWCI.class).getIdsVentas(TENNANT_TOKEN
                , HTTP_HEADER_BEARER + TOKEN
                , fecha[0], fecha[1], fecha[2]).execute();
        return handleResponse(resp);
    }

    public VentaResumenModel getResumenVentas(int idVenta) throws Exception {
        Response<VentaResumenModel> resp = retrofit.create(ResumenVentasWCI.class).getResumenGeneralFrom(TENNANT_TOKEN
                , HTTP_HEADER_BEARER + TOKEN
                , idVenta).execute();
        return handleResponse(resp);
    }

    public List<DetallesVentasModel> getDetallesPorArea(int idVenta, String areaCod) throws Exception {
        Response<List<DetallesVentasModel>> resp = retrofit.create(ResumenVentasWCI.class).getDetailsFromArea(TENNANT_TOKEN
                , HTTP_HEADER_BEARER + TOKEN
                , idVenta, areaCod).execute();
        return handleResponse(resp);
    }

    public List<DetallesVentasModel> getDetallesPorDependientes(int idVenta, String usuario) throws Exception {
        Response<List<DetallesVentasModel>> resp = retrofit.create(ResumenVentasWCI.class).getDetailsFromDpte(TENNANT_TOKEN
                , HTTP_HEADER_BEARER + TOKEN
                , idVenta, usuario).execute();
        return handleResponse(resp);
    }

    public List<DetallesVentasModel> getDetallesPorCocina(int idVenta, String cocinaCod) throws Exception {
        Response<List<DetallesVentasModel>> resp = retrofit.create(ResumenVentasWCI.class).getDetailsFromPtoElaboracion(TENNANT_TOKEN
                , HTTP_HEADER_BEARER + TOKEN
                , idVenta, cocinaCod).execute();
        return handleResponse(resp);
    }

    public List<DetallesVentasModel> getDetallesPor(int idVenta) throws Exception {
        Response<List<DetallesVentasModel>> resp = retrofit.create(ResumenVentasWCI.class).getDetailsGeneral(TENNANT_TOKEN
                , HTTP_HEADER_BEARER + TOKEN
                , idVenta).execute();
        return handleResponse(resp);
    }

}
