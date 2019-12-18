package com.services.parsers;

import com.services.models.*;

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
            TAGNUMEROCOMENSAL = "numeroComensal",TAGNOTA = "nota",
            TAGORDEN = "orden",
            TAGMESA ="mesa";



    public ProductoVentaOrdenXMLParser() {
        super("productovOrden", "productovOrdens");
    }



    @Override
    public ProductoVentaOrdenModel readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        OrdenModel o = new OrdenModel();
        ProductoVentaModel pv = null;
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



    ProductoVentaOrdenPKModel pk = new ProductoVentaOrdenPKModel(pv.getPCod(),o.getCodOrden());
    ProductoVentaOrdenModel po = new ProductoVentaOrdenModel(pk,Float.parseFloat(cantidad));
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
