package com.services.web_connections;

import com.services.models.SeccionModel;

import java.util.List;

/**
 * Created by Jorge on 2/7/17.
 */
public class ProductoWCS extends SimpleWebConnectionService {

    final String P = "productoventa/";

    /**
     * Etiquetas a llamar.
     */
    final String PRODUCTS = "PRODUCTS";

    public ProductoWCS() {
        super();
        path += P;
    }

    public List<ProductoVentaModel> getProducts(String codMesa) throws Exception {
        String URL = path + PRODUCTS + "?codMesa=" + codMesa;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, ProductoVentaModel.class));
    }
}
