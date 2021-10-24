package com.services.web_connections.interfaces;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SiteInfoWCI {

    @GET("info")
    Call<HashMap<String,Object>> getSiteVersion(@Header("Tennant") String bearerTennantToken
            ,@Header("Authorization") String basicAndToken);
}
