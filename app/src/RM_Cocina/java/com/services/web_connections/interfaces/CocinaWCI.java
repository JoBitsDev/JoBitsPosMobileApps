package com.services.web_connections.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CocinaWCI {

    public final String COCINA_PATH = "punto_elaboracion_list/";

    @GET(COCINA_PATH + "list/names")
    Call<String[]> listNames(@Header("Tennant") String tennantToken,@Header("Authorization") String bearerToken);


}
