package com.services.web_connections;


import com.services.web_connections.interfaces.CocinaWCI;

public class CocinaWCS extends RetrofitBaseConection {

    private final CocinaWCI service = retrofit.create(CocinaWCI.class);

    public CocinaWCS() {
        super();
    }

    public String[] getCocinasNames() throws Exception {
        return handleResponse(service.listNames(TENNANT_TOKEN, getBearerToken()).execute());
    }

}
