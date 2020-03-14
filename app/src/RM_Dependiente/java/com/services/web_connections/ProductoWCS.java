package com.services.web_connections;

import com.services.models.ProductoVentaModel;
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
    final String PRODUCTS = "PRODUCTS",
            RESTANTES = "RESTANTES";

    public ProductoWCS() {
        super();
        path += P;
    }

    public List<ProductoVentaModel> getProducts(String codArea) throws Exception {
        String URL = path + PRODUCTS + "?codArea=" + codArea;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, ProductoVentaModel.class));
    }

    public int getRestantes(String codProd) throws Exception {
        String URL = path + RESTANTES + "?codProducto=" + codProd;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, Integer.class);
    }
}
