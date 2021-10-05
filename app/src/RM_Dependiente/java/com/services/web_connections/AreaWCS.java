package com.services.web_connections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.services.models.orden.MesaModel;
import com.services.web_connections.interfaces.AreaWCI;

import java.util.List;

/**
 * Created by Jorge on 2/7/17.
 */
public class AreaWCS extends RetrofitBaseConection {

    AreaWCI service = retrofit.create(AreaWCI.class);

    /**
     * Etiquetas a llamar.
     */

    public void saveMesasList(List<MesaModel> mesaModels, String area) throws JsonProcessingException {
        // saveResponse(URL, om.writeValueAsString(mesaModels));
    }

    public String[] getAreasName() throws Exception {
        return handleResponse(service.getAreasNames(TENNANT_TOKEN, HTTP_HEADER_BEARER + TOKEN).execute());
    }

    public String[] findVacias(String codMesa) throws Exception {
        return handleResponse(service.findVacias(TENNANT_TOKEN, HTTP_HEADER_BEARER + TOKEN, codMesa).execute());
    }


    public List<MesaModel> findMesas(String selectedArea) throws Exception {
        return handleResponse(service.getMesasFromArea(TENNANT_TOKEN, HTTP_HEADER_BEARER + TOKEN, selectedArea).execute());
    }

}
