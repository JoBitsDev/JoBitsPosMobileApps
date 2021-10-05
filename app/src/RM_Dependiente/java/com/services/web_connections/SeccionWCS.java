package com.services.web_connections;

import com.services.models.orden.SeccionModel;
import com.services.web_connections.interfaces.SeccionWCI;

import java.util.List;

/**
 * Created by Jorge on 2/7/17.
 */
public class SeccionWCS extends RetrofitBaseConection {

    public List<SeccionModel> getSecciones() throws Exception {
        return handleResponse(retrofit.create(SeccionWCI.class).findAll(TENNANT_TOKEN,HTTP_HEADER_BEARER+TOKEN).execute());
    }

}
