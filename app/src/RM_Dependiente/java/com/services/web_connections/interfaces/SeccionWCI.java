package com.services.web_connections.interfaces;


import com.services.models.orden.SeccionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SeccionWCI {

    public static final String SECCION_LIST_PATH = "seccion-list/";

    @GET(SECCION_LIST_PATH + "list")
    Call<List<SeccionModel>> findAll(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken);

    @GET(SECCION_LIST_PATH + "list/by-mesa/{idMesa}")
    Call<List<SeccionModel>> findByMesa(@Header("Tennant") String tennantToken
            , @Header("Authorization") String bearerToken
            , @Path("idMesa") String byMesa);

}