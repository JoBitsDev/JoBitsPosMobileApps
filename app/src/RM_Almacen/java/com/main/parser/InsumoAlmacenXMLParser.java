package com.main.parser;

import com.main.objetos.Insumo;
import com.main.objetos.InsumoAlmacen;
import com.main.objetos.InsumoAlmacenPK;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 2/7/17.
 */
public class InsumoAlmacenXMLParser extends AbstractXmlParser<InsumoAlmacen> {

    // We don't use namespaces
    private static final String
            CANTIDAD = "cantidad",
            VALOR_MON = "valorMonetario",
            ALMACEN = "almacen",
            INSUMO = "insumo",
            INSUMO_ALMACEN_PK = "insumoAlmacenPK";

    public InsumoAlmacenXMLParser() {
        super("insumoAlmacen", "insumoAlmacens");
    }


    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    public InsumoAlmacen readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        String cantidad = "0";
        String valor_mon = null;
        Insumo i = null;
        InsumoAlmacenPK pk = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(CANTIDAD)) {
                cantidad = readString(parser, CANTIDAD);
            } else if (name.equals(VALOR_MON)) {
                valor_mon = readString(parser, VALOR_MON);
            } else if (name.equals(INSUMO)) {
                i = new insumoXMLParser().readEntry(parser);
            }else if (name.equals(INSUMO_ALMACEN_PK)) {
                pk = new InsumoAlmacenPKXMLParser().readEntry(parser);
            }else {
                skip(parser);
            }
        }
        return new InsumoAlmacen(pk,Float.parseFloat(cantidad),Float.parseFloat(valor_mon), i);
    }




}














