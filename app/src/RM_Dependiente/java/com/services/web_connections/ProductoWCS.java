package com.services.web_connections;

import com.services.models.orden.ProductoVentaModel;
import com.services.web_connections.interfaces.ProductoWCI;

import java.util.List;

/**
 * Created by Jorge on 2/7/17.
 */
public class ProductoWCS extends RetrofitBaseConection {


    public List<ProductoVentaModel> getProducts(String codArea) throws Exception {
        return handleResponse(retrofit.create(ProductoWCI.class)
                .findAllByArea(TENNANT_TOKEN,HTTP_HEADER_BEARER + TOKEN,codArea).execute());
    }

    public int getRestantes(String codProd) throws Exception {
        return handleResponse(retrofit.create(ProductoWCI.class)
                .getRestantes(TENNANT_TOKEN,getBearerToken(),codProd).execute());
    }
}
