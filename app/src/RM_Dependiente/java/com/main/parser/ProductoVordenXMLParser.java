package com.main.parser;

import com.main.objetos.Orden;
import com.main.objetos.ProductoVenta;
import com.main.objetos.ProductovOrden;
import com.main.objetos.ProductovOrdenPK;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 27/9/17.
 */

public class ProductoVordenXMLParser extends AbstractXmlParser<ProductovOrden> {

    private final String
            CANTIDAD = "cantidad",
            ENVIADOSACOCINA = "enviadosacocina",
            TAGPRODUCTOVENTA = "productoVenta",
            TAGNUMEROCOMENSAL = "numeroComensal",
            TAGORDEN = "orden";

    String id;

    public ProductoVordenXMLParser(String id) {
        super("productovOrden", "productovOrdens");
        this.id = id;
    }



    @Override
    public ProductovOrden readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        Orden o = new Orden();
        ProductoVenta pv = null;
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



    ProductovOrdenPK pk = new ProductovOrdenPK(pv.getPCod(),o.getCodOrden());
    ProductovOrden po = new ProductovOrden(pk,Float.parseFloat(cantidad));
    po.setEnviadosACocina(Float.parseFloat(enviadosCocina));
        po.setNumero_comensal(Integer.valueOf(numeroComensal));
    po.setOrden(o);
    po.setProductoVenta(pv);
            ENTITY = "productovOrden";
            ENTITIES = "productovOrdens";
        return po;


    }

}
