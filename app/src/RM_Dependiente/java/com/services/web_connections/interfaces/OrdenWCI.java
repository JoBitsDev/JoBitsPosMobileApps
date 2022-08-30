package com.services.web_connections.interfaces;


import com.services.models.VentaId;
import com.services.models.orden.OrdenModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrdenWCI {

    public final String ORDEN_PATH = "pos/orden/";


    @POST("pos/venta/{id}/create-new-orden/{codMesa}")
    Call<OrdenModel> create(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken,
                            @Path("id") int idVenta, @Path("codMesa") String codMesa);

    @GET("pos/venta-list/venta-abierta")
    Call<VentaId> resolveAbierta(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken);

    @PUT(ORDEN_PATH + "{id}/cerrar-orden/{ticket}/{cash}/{tarjeta}")
    Call<OrdenModel> finish(@Header("Tennant") String tennantToken,
                            @Header("Authorization") String bearerToken,
                            @Path("id") String idOrden,
                            @Path("ticket") boolean imprimirTicket,
                            @Path("cash") float pagadoCash,
                            @Path("tarjeta") float pagadoTarjeta);

    @POST(ORDEN_PATH + "{id}/add-product/{idProducto}/{cantidad}/-1")
    Call<OrdenModel> addToOrden(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden
            , @Path("idProducto") String idProducto
            , @Path("cantidad") float cantidad);

    @DELETE(ORDEN_PATH + "{id}/remove-product/{idProducto}/{cantidad}")
    Call<OrdenModel> deleteFromOrden(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden
            , @Path("idProducto") int idProducto
            , @Path("cantidad") float cantidad);

    @PUT(ORDEN_PATH + "{id}/enviar-a-cocina/127.0.0.1")
    Call<Void> enviarCocina(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden);

    @GET(ORDEN_PATH + "find/{id}")
    Call<OrdenModel> find(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden);

    @PUT(ORDEN_PATH + "{id}/mover-a/{idMesa}")
    Call<Void> moverAMesa(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden
            , @Path("idMesa") String idMesa);


    @PUT(ORDEN_PATH + "{id}/set-autorizo/{boolValue}")
    Call<Void> setDeLaCasa(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden
            , @Path("boolValue") boolean boolValue);


    @POST(ORDEN_PATH + "{id}/add-nota/{idProducto}/{nota}")
    Call<Void> addNota(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden
            , @Path("idProducto") int idProducto
            , @Path("nota") String nota);

    @GET(ORDEN_PATH + "{id}/get-nota/{idProducto}")
    Call<Map<String, String>> getNota(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden
            , @Path("idProducto") int idProducto);

    @POST(ORDEN_PATH + "{id}/ceder/{user}")
    Call<Boolean> cederUsuario(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden
            , @Path("user") String user);

    @GET(ORDEN_PATH + "{id}/validate")
    Call<OrdenModel> validate(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("id") String idOrden);

}
