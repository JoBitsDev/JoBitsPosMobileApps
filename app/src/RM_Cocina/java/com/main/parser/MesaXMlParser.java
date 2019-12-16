package com.main.parser;

import com.main.Mesa;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 2/7/17.
 */
public class MesaXMlParser extends AbstractXmlParser<Mesa> {

    // We don't use namespaces
    private static final String
            CAPACIDAD_MAX = "capacidadMax",
            COD_MESA = "codMesa",
            ESTADO = "estado";

    public MesaXMlParser() {
    super("mesa","mesas");
    }



    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    public Mesa readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        String capacidadMax = "0";
        String codMesa = null;
        String estado = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(CAPACIDAD_MAX)) {
                capacidadMax = readString(parser,CAPACIDAD_MAX);
            } else if (name.equals(COD_MESA)) {
                codMesa = readString(parser,COD_MESA);
            } else if (name.equals(ESTADO)) {
                estado = readString(parser,ESTADO);
            } else {
                skip(parser);
            }
        }
        return new Mesa(codMesa, estado , Integer.parseInt(capacidadMax));
    }

    public Mesa readEntry(XmlPullParser parser,String start) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, start);
        String capacidadMax = "0";
        String codMesa = null;
        String estado = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(CAPACIDAD_MAX)) {
                capacidadMax = readString(parser,CAPACIDAD_MAX);
            } else if (name.equals(COD_MESA)) {
                codMesa = readString(parser,COD_MESA);
            } else if (name.equals(ESTADO)) {
                estado = readString(parser,ESTADO);
            } else {
                skip(parser);
            }
        }
        return new Mesa(codMesa, estado , Integer.parseInt(capacidadMax));
    }





}














