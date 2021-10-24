package com.services.web_connections.interfaces;


import com.services.models.orden.MesaModel;
import com.services.models.orden.ProductoVentaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductoWCI {

    public final String PRODUCTO_VENTA_LIST_PATH = "producto-venta/";


    @GET(PRODUCTO_VENTA_LIST_PATH + "list/by-area/{idArea}")
    Call<List<ProductoVentaModel>> findAllByArea(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken, @Path("idArea") String idArea);

    @GET(PRODUCTO_VENTA_LIST_PATH + "{idProducto}/restantes")
    Call<Integer> getRestantes(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken,@Path("codMesa") String codMesa);

}