package com.main.parser;

import com.main.Mesa;
import com.main.Orden;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 3/10/17.
 */

public class OrdenXMLParser extends AbstractXmlParser<com.main.Orden> {


    private final String
            CODORDEN = "codOrden",
            DELACASA = "deLaCasa",
            TAGMESA = "mesa",
            TAGPERSONAL = "personalusuario",
            TAGVENTA = "ventafecha";


    public OrdenXMLParser() {
        super("orden","ordens");
    }


    public Orden readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        String codOrden = null;
        boolean deLaCasa = false;
        Mesa m = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(CODORDEN)) {
                codOrden = readString(parser, CODORDEN);
            } else if (name.equals(DELACASA)) {
                String aux = readString(parser, DELACASA);
                deLaCasa = aux.equals("true");
            } else if (name.equals(TAGMESA)) {
                m = new MesaXMlParser().readEntry(parser,"mesa");
            }else {
                skip(parser);
            }
        }

        Orden  o = new Orden(codOrden,deLaCasa);
        o.setMesacodMesa(m);
        return o;

    }
}
