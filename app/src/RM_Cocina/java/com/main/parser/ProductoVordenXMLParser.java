package com.main.parser;

import com.main.Orden;
import com.main.ProductoVenta;
import com.main.ProductovOrden;
import com.main.ProductovOrdenPK;

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
            TAGNUMEROCOMENSAL = "numeroComensal",TAGNOTA = "nota",
            TAGORDEN = "orden",
            TAGMESA ="mesa";



    public ProductoVordenXMLParser() {
        super("productovOrden", "productovOrdens");
    }



    @Override
    public ProductovOrden readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        Orden o = new Orden();
        ProductoVenta pv = null;
        String cantidad = null, enviadosCocina = null,numeroComensal = null,nota = null;


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
            }else if(name.equals(TAGNOTA)){
                nota = readString(parser,TAGNOTA);
            }
            else
                if (name.equals(TAGORDEN)) {
                XmlPullParser aux = parser;

                o = new OrdenXMLParser().readEntry(aux);

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
    po.setNota(nota);
            ENTITY = "productovOrden";
            ENTITIES = "productovOrdens";
        return po;


    }

}
