package com.services.web_connections.interfaces;

import com.services.models.DetallesVentasModel;
import com.services.models.VentaResumenModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ResumenVentasWCI {

    public static final String VENTA_LIST_PATH = "venta-list/";

    @GET(VENTA_LIST_PATH + "{id}/resumen-general")
    Call<VentaResumenModel> getResumenGeneralFrom(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") int idVenta);

    @GET(VENTA_LIST_PATH + "ids/{aaaa}/{mm}/{dd}")
    Call<List<Integer>> getIdsVentas(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("aaaa") int anno
            , @Path("mm") int mes
            , @Path("dd") int dia);

    @GET(VENTA_LIST_PATH + "{id}/detalles-area/{idArea}")
    Call<List<DetallesVentasModel>> getDetailsFromArea(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") int ventaId
            , @Path("idArea") String idArea);

    @GET(VENTA_LIST_PATH + "{id}/detalles-mesero/{idUsuario}")
    Call<List<DetallesVentasModel>> getDetailsFromDpte(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") int ventaId
            , @Path("idUsuario") String idDpte);

    @GET(VENTA_LIST_PATH + "{id}/detalles-punto-elaboracion/{idPuntoElab}")
    Call<List<DetallesVentasModel>> getDetailsFromPtoElaboracion(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") int ventaId
            , @Path("idPuntoElab") String idPuntoElab);

    @GET(VENTA_LIST_PATH + "{id}/resumen-detallado")
    Call<List<DetallesVentasModel>> getDetailsGeneral(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") int ventaId);

}
