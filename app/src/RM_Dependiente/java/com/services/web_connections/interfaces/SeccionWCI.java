package com.services.web_connections.interfaces;


import com.services.models.orden.SeccionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SeccionWCI {

    public static final String SECCION_LIST_PATH = "area-venta/";

    @GET(SECCION_LIST_PATH + "list")
    Call<List<SeccionModel>> findAll(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken);

}