package com.main.parser;

import com.main.Insumo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 27/9/17.
 */

public class insumoXMLParser extends AbstractXmlParser<Insumo> {

    private final String
            COD_INSUMO = "codInsumo",
            COSTO_POR_UNIDAD = "costoPorUnidad",
            NOMBRE = "nombre",
            STOCK_ESTIMATION = "stockEstimation",
            UM = "um";

    public insumoXMLParser() {
        super("insumo", "insumos");

    }


    @Override
    public Insumo readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        Insumo i;
        String cod_insumo = null,
                costo_por_unidad = null,
                um = null,
                nombre = null,
                stock_estimation = "0";


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(COD_INSUMO)) {
                cod_insumo = readString(parser, COD_INSUMO);
            } else if (name.equals(COSTO_POR_UNIDAD)) {
                costo_por_unidad = readString(parser, COSTO_POR_UNIDAD);
            } else if (name.equals(STOCK_ESTIMATION)) {
                stock_estimation = readString(parser, STOCK_ESTIMATION);
            } else if (name.equals(UM)) {
                um = readString(parser, UM);
            } else if (name.equals(NOMBRE)) {
                nombre = readString(parser, NOMBRE);
            } else {
                skip(parser);
            }
        }


        i = new Insumo(cod_insumo, nombre, um, Float.parseFloat(costo_por_unidad),Float.parseFloat(stock_estimation));

        ENTITY = "insumo";
        ENTITIES = "insumos";
        return i;


    }

}
