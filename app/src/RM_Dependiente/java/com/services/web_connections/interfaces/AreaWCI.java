package com.services.web_connections.interfaces;


import com.services.models.orden.MesaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface AreaWCI{

    public static final String AREA_LIST_PATH = "area-venta/";


    @GET(AREA_LIST_PATH + "list/names")
    Call<String[]> getAreasNames(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken);

    @GET(AREA_LIST_PATH + "mostrar-vacias/{codMesa}/names")
    Call<String[]> findVacias(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken,@Path("codMesa") String codMesa);

    @GET(AREA_LIST_PATH + "{idArea}/list-mesas")
    Call<List<MesaModel>> getMesasFromArea(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken, @Path("idArea") String idArea);
}