package com.services.parsers;

import com.services.models.OrdenModel;
import com.services.models.ProductoVentaModel;
import com.services.models.ProductoVentaOrdenModel;
import com.services.models.ProductovOrdenPKModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 27/9/17.
 */

public class ProductoVentaOrdenXMLParser extends AbstractXmlParser<ProductoVentaOrdenModel> {

    private final String
            CANTIDAD = "cantidad",
            ENVIADOSACOCINA = "enviadosacocina",
            TAGPRODUCTOVENTA = "productoVenta",
            TAGNUMEROCOMENSAL = "numeroComensal",
            TAGORDEN = "orden";

    String id;

    public ProductoVentaOrdenXMLParser(String id) {
        super("productovOrden", "productovOrdens");
        this.id = id;
    }



    @Override
    public ProductoVentaOrdenModel readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        OrdenModel o = new OrdenModel();
        ProductoVentaModel pv = null;
        String cantidad = null, enviadosCocina = null,numeroComensal = null;


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(CANTIDAD)) {
                cantidad = readString(parser, CANTIDAD);
            } else if (name.equals(ENVIADOSACOCINA)) {
                enviadosCocina = readString(parser, ENVIADOSACOCINA);
            } else if (name.equals(TAGNUMEROCOMENSAL)) {
                numeroComensal = readString(parser, TAGNUMEROCOMENSAL);
            }else if (name.equals(TAGPRODUCTOVENTA)) {
                pv = new ProductoVentaXMlParser().readEntry(parser);
            } else if (name.equals(TAGORDEN)) {
                XmlPullParser aux = parser;

                o = new OrdenXMLParser().readEntry(parser);

            }
            else{
                skip(parser);
            }
        }



    ProductovOrdenPKModel pk = new ProductovOrdenPKModel(pv.getPCod(),o.getCodOrden());
    ProductoVentaOrdenModel po = new ProductoVentaOrdenModel(pk,Float.parseFloat(cantidad));
    po.setEnviadosACocina(Float.parseFloat(enviadosCocina));
        po.setNumero_comensal(Integer.valueOf(numeroComensal));
    po.setOrdenModel(o);
    po.setProductoVentaModel(pv);
            ENTITY = "productovOrden";
            ENTITIES = "productovOrdens";
        return po;


    }

}
