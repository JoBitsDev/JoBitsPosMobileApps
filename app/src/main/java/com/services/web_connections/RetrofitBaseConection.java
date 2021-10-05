package com.services.web_connections;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.utils.EnvironmentVariables;
import com.utils.exception.ServerErrorException;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitBaseConection {

    /**
     * Tiempo maximo esperado para la lectura.
     */
    public static final int MAX_READ_TIME = 5 * 1000;
    /**
     * Tiempo maximo esperado para la respuesta del servidor.
     */
    public static final int MAX_RESPONSE_TIME = 3 * 1000;
    public static final String DIR_CACHE = EnvironmentVariables.PERSISTENCE_PATH + "/";
    protected static final ObjectMapper oMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    protected static Retrofit retrofit;
    /**
     * Token para las llamandas seguras al servidor.
     */
    protected static String TOKEN = null;
    /**
     * Token para autenticarse en el servidor y que este lo redirija hacia la base de datos correspondiente
     */
    protected static String TENNANT_TOKEN = null;
    protected final String HTTP_HEADER_LOCATION = "Location";
    protected final String HTTP_HEADER_TENNANT_ID = "TennantId ";
    protected final String HTTP_HEADER_AUTHORITATION = "Authorization";
    protected final String HTTP_HEADER_BEARER = "Bearer ";
    protected final String HTTP_HEADER_BASIC = "Basic ";

    public static void setRetrofit(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(oMapper))
                .build();
    }

    protected <T> T handleResponse(Response<T> resp) throws ServerErrorException {
        if (resp.isSuccessful()) {
            return resp.body();
        } else {
            try {
                throw new ServerErrorException(resp.errorBody().string(), resp.code());
            } catch (IOException ex) {
                throw new ServerErrorException(resp.message(), resp.code());
            }
        }
    }

    protected String getBearerToken() {
        return HTTP_HEADER_BEARER + TOKEN;
    }
}
