package com.services.web_connections.interfaces;

import com.services.models.Token;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PersonalWCI {

    @GET("personal/list-online/names")
    Call<List<String>> getToken(@Header("Tennant") String tennantToken, @Header("Authorization") String base64Credentials);

}
