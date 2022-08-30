package com.services.web_connections.interfaces;


import com.services.models.orden.ProductoVentaOrdenModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationWCI {

    public static final String NOTIFICACION_PATH = "pos/notifications/";


    @GET(NOTIFICACION_PATH + "{cod_cocina}/get-pending")
    Call<List<ProductoVentaOrdenModel>> getNotifications(
            @Header("Tennant") String tennantToken,
            @Header("Authorization") String bearerToken,
            @Path("cod_cocina") String codCocina);

    @POST(NOTIFICACION_PATH + "notify/{cod_orden}/{id_producto_orden}/{cantidad}")
    Call<List<String>> notifiOfCompletition(
            @Header("Tennant") String tennantToken,
            @Header("Authorization") String bearerToken,
            @Path("cod_orden") String codOrden,
            @Path("id_producto_orden") int idProductoOrden,
            @Path("cantidad") float cantidad);

}
